# Court-Analysing

[toc]

> 【Data Science】南京大学2021数据科学基础大作业

## 简要介绍

这次大作业是实现分析“中国裁判文书网”的一个任务，主要采用了前后端分离设计，后端使用了`SpringBoot`来进行服务端的管理，因为不需要存储数据所以就没有使用数据库。

下面会简要介绍一下在此次大作业中的一些技术栈和遇到的困难和如何解决的。



## NLP

在NLP分词方面，主要使用的是`HanLP`来进行分词和划分词性，调用的是其中的标准分词器，如下所示：

```java
List<Term> termList = StandardTokenizer.segment(“商品和服务”);
```

这里的`segment()`方法就是将参数进行分词且获得词性，下面会简要介绍一下这种分词的原理，用到的是中文分词中比较常见的**词典**和**词图生成**



### 算法详解

#### 词典的生成

首先是词典的生成是在`HanLP`中带有了基础的词典之上添加了许多有关**法律**方面的专有名词，比如一些**罪行**的名字和一些判罚的条例等专有名词。当然，词典并不能够做到百分百完美的分词，我们可以举一个例子来说明词典可能出现的错误：

> 对于“川普”这个词，如果在词典中的话，那么在分词阶段其就极有可能会被分成结果（在只用词典的时候），但在不同的语境中，这个词的意思其实是不一样的，比如如下的两种情况
>
> 1. 你的四川普通话真好
> 2. 美国总统川普
>
> 很明显，在这两句话之中，“川普”这个词有着不同的含义，如果在第一个语境中将“川普”作为一个完整的词分在一起，显然就破坏了句子原本的含义。

针对这种情况，`HanLP`使用了多种方式来减少这种情况的出现。首先，`HanLP`使用了词性 + 频率的方式来记录词典，即对于相同的几个字，其中的组合可能有些的频数会比较大，有些频数会比较小。`HanLP`基于此来记录词典，可以比较有效的让某些出现次数较多的组合不会被误判。`HanLP`支持用户动态添加词典以及自定义词典，也可以自己确定优先级，这让我们要在特定领域使用特定的词典能够进行灵活的调整。

其次，`HanLP`使用了词图的生成方式来进行分析，这也是下面所要讲的

#### 词图的生成

当分词系统有一份词典的时候，就可以生成词图了。所谓词图，指的是句子中所有词可能构成的图。如果一个词A的下一个词可能是B的话，那么A和B之间具有一条路径E(A,B)。一个词可能有多个后续，同时也可能有多个前驱，它们构成的图在`HanLP`中称作词图。

需要稀疏2维矩阵模型，以一个词的起始位置作为行，终止位置作为列，可以得到一个二维矩阵。例如：“他说的确实在理”这句话

![img](http://ww3.sinaimg.cn/large/6cbb8645gw1eghe5t76jrg20cs06dmwz.gif)

那么如何建立节点之间的联系呢？也就是如何找到一个词A的后续B、C、D……呢？有两种已实现的方法，一种是所谓的DynamicArray法，一种是快速offset法。

##### DynamicArray法

最直截了当的想法当然是用二维数组模拟这个模型了，很明显，其中有不少空洞，所以在ICT系列的分词器中定义了一个蹩脚的DynamicArray结构用来储存模型，DynamicArray结构的每个节点包含一个个词的row和col，待会儿看完offset法你就会明白我什么么说DynamicArray蹩脚。

在这张图中，行和列有一个非常有意思的关系：col为 n 的列中所有词可以与row为 n 的所有行中的词进行组合。例如“的确”这个词，它的col = 5，需要和它计算平滑值的有两个，分别是row = 5的两个词：“实”和“实在”。

连接词形成边的时候，利用上面提到的关系即可。

但是在遍历和插入的时候，需要一个个比较col和row的关系，复杂度是O(N)。

##### 快速offset

虽然模型的表示用DynamicArray没有信息的损失，但问题是，真的需要表示模型吗？

当然不，可以将起始offset相同的词写到一行：

```
始##始
他
说
的/的确
确/确实
实/实在
在/在理
理
末##末
```

这个储存起来很简单，一个一维数组，每个元素是一个单链表。

怎么知道“的确”的下一个词是什么呢？“的确”的行号是4,长度是2,4+2=6，于是第六行的两个词“实/实在”就是“的确”的后续。就这么简单。

同时这种方法速度非常快，插入和查询的时间都是O(1)。



##### reference

1. [HanLP官方文档](http://www.hankcs.com/nlp/segment/the-word-graph-is-generated.html)
2. [NLP中文分词原理及分词算法](https://www.cnblogs.com/DianaCody/p/5425624.html)



#### 地名的识别

地名识别的算法比较复杂，涉及到了HMM等，通过训练对地名进行标注然后统计词频得到词典，将多个HMM模型叠加可以发挥更加精准的效果。



## 前后端通信

### 后端部分

后端部分采用了`SpringBoot`来进行开发，几个重要的接口如下所示：

1. 获取文本分析的结果

	```java
	/**
	 * 返回词性分析的结果
	 * @param text 待分析的文书
	 * @return 词性分析结果，词性 - 对应的单词
	 */
	@RequestMapping(value = "/getResult", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public Map<String, List<String>> textAnalysis(@RequestParam(value = "text") String text) {
		// 清除之前的分析内容
	    analysis.clear();
	    analysis.setParagraph(text);
	    analyse();
	    return analysis.getRes();
	}
	```

2. 上传文件进行分析

	```java
	/**
	 * 接受一个文件的传输，返回词法分析的结果
	 * @param uploadFile 用户上传的从前端接收的文件
	 * @return 词法分析的结果
	 * @throws IOException 文件接收异常
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public Map<String, List<String>> uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
	    if (uploadFile == null) {
	        // 接收失败
	        return null;
	    }
	
	    InputStream inputStream = uploadFile.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
	    StringBuilder temp = new StringBuilder();
	
	    while (reader.ready()) {
	        temp.append(reader.readLine());
	    }
	
	    // analyse
	    String content = temp.toString();
	    return textAnalysis(content);
	}
	```

3. 爬取文书内容

	```java
	/**
	 * 爬取文书内容
	 * @param searchContent 搜索的关键词信息
	 * @return 文本内容
	 */
	@RequestMapping(value = "/reptile", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public String reptile(@RequestParam("searchContent") String searchContent) throws InterruptedException {\
	    reptile.clearContent();
	    reptile.reptile(searchContent);
	    return reptile.getContent();
	}
	```

	

### 前端部分



## 爬虫





## 研究过程



