
    var beginAnalyse = document.getElementById('beginAnalyse');
    var noun = document.getElementById('noun');
    var verb = document.getElementById('verb');
    var adj = document.getElementById('adj');


    let currentMark; // 用于记录当前的标注信息
    //word用于接收NLP返回的信息
    var words = {
        noun : ['周永华','男','汉族','贵州省威宁彝族回族苗族自治县','云南省高级人民法院','中华人民共和国最高法院','......','......'],
        verb : ['审理','指控','认定','判处','剥夺','提出','消灭','处死','击打'],
        adj : ['严重','恶劣','可恶','糟糕'],
    }

    // 根据NLP分析结果（对象格式），将其中数据以复选框格式创建子节点
    beginAnalyse.onclick = function NLP() {
        // 将textField内的值传给NLP程序，返回结果赋给words对象
        let textField = document.getElementById('textField');
        let content = textField.value; // content即为输入框中的文字，类型为字符串
        let formdata = new FormData;
        formdata.append('text', content)
        let url = "http://localhost:8080/getResult";
        jQuery.ajax({
            type: 'post',
            url: url,
            data: formdata,
            contentType: false,
            processData: false,
            success: function (data) {
                alert("success");
                words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
                console.log(words)
                formLabels(words)
            }
        })

    }

    // 根据word的内容动态创建节点
     function formLabels(words) {
            for(let key in words){
                console.log(key)
                var currentArr = words[key] // 得到当前对应数组
                var leftUl = document.createElement("ul")
                var rightUl = document.createElement('ul')
                // 数组中的每一个词都建一个li，然后挂到对应的ul上
                for (let index = 0; index < currentArr.length; index++) {
                    var li = document.createElement('li');
                    var checkBox = document.createElement('input')
                    checkBox.setAttribute("type","checkbox");
                    var labelCode = '<label class="markLabels" value="' + currentArr[index] + '">'
                    var htmlCode = labelCode + ' <input type="checkBox" class="theWords" value="'+currentArr[index]+'">' + currentArr[index] + '</input>' + '</label >'
                    li.innerHTML =htmlCode

                  // li.appendChild(checkBox)

                    if(index % 2 != 1) leftUl.appendChild(li)
                    else rightUl.appendChild(li)

                }
                var leftBox = document.querySelector('#' + key +'> .leftOne')
                var rightBox = document.querySelector('#' + key +'> .rightOne')
                leftBox.appendChild(leftUl)
                rightBox.appendChild(rightUl)
            }
        }

// 实现点击上传按钮，将文本内容同步到输入框，同时提交文件进行分析
// todo:如果有需要的话，可以设置一个全局变量theFile，在点击开始分析之后再把数据文件传过去。
// error:传输过去后分词结果有误
    function upLoadFile() {
        var file = document.getElementById("submitLocalFile")
        var textAera = document.querySelector('#textField')
        var theFile
        // 实现将文件的内容显示在文本框内
        var reader = new FileReader
        console.log('提交按钮是')
        console.log(this.file)
        console.log('提交的所有文件是')
        console.log(file.files)
        console.log('提交的文件是')
        console.log(file.files[0])
        theFile = file.files[0]


        reader.readAsText(file.files[0],'utf-8')
        reader.onload = function (event) {
            var data = event.target.result;
            // console.log(event)
            // console.log(event.target);
            // console.log("data is" + data)
            textAera.innerHTML = data
        }

        // 实现直接将文件传输到8080端口
        console.log(theFile)
        let formdata = new FormData;
        formdata.append('uploadFile',theFile)
        console.log(formdata.get('uploadFile'))
        let url = 'http://localhost:8080/uploadFile'; // todo:根据你的文件来确定url

        jQuery.ajax({
            type: 'post',
            url: url,
            data: formdata,
            contentType: false,
            processData: false,
            success: function (data) {
                alert("success");
                console.log(data)
                words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
                formLabels(words)
            }
        })
    }
