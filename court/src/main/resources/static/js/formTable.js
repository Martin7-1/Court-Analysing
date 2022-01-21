
    var beginAnalyse = document.getElementById('beginAnalyse');
    var noun = document.getElementById('noun');
    var verb = document.getElementById('verb');
    var adj = document.getElementById('adj');
    // 在if的条件中，underfined，null，“”，等价于false
    var theFiles;
    let currentMark; // 用于记录当前的标注信息
    var useContentOfTextarea = false; // 用于使得在多个文件的情况下，开始分析按钮只分析当前文本框内容，无法分析下一个文件
    //word用于接收NLP返回的信息
    var words = {
        noun : ['周永华','男','汉族','贵州省威宁彝族回族苗族自治县','云南省高级人民法院','中华人民共和国最高法院','......','......'],
        verb : ['审理','指控','认定','判处','剥夺','提出','消灭','处死','击打'],
        adj : ['严重','恶劣','可恶','糟糕'],
    }

    function rawTheWords() {
        words = {
            noun:[],
            verb:[],
            adj:[]
        }

    }

    // 根据NLP分析结果（对象格式），将其中数据以复选框格式创建子节点
    beginAnalyse.onclick = function NLP() {
        rawTheWords()
        console.log("这是开始分析按钮绑定的第一个事件")
        console.log("接下来删除现有的所有标签")
        var leftParent = document.getElementsByClassName('leftOne')
        var rightParent = document.getElementsByClassName('rightOne')
        removeAllLabels(leftParent)
        removeAllLabels(rightParent)

        if (!theFiles || useContentOfTextarea) {
            console.log("未上传文件，分析文本框内容")
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
                    // alert("success");
                    words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
                    console.log(words)
                    formLabels(words)
                    afterAnalyse() // afterAnalyse()函数位于formMarkFile中，用于再生成新标签后重新获取标签，辅助实现标注文件生成

                }
            })
        }else{
            // 若是多个文件这边只分析第一个文件
            console.log("上传文件，分析首个文件")
            // 实现直接将文件传输到8080端口
            console.log(theFiles[0])
            let formdata = new FormData;
            formdata.append('uploadFile',theFiles[0])
            console.log(formdata.get('uploadFile'))
            let url = 'http://localhost:8080/uploadFile'; // todo:根据你的文件来确定url

            jQuery.ajax({
                type: 'post',
                url: url,
                data: formdata,
                contentType: false,
                processData: false,
                success: function (data) {
                    // alert("success");
                    console.log(data)
                    words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
                    formLabels(words)
                    afterAnalyse()
                }
            })

        }


    }

    //用于多个文件，在一次性上传多个文件，保存第一个文件的案例与标识后，自动分析下一个文件并生成标签
    function autoNLP(fileth) {
        console.log("清空words")
        rawTheWords()
        console.log("清空后的words为")
        console.log(words)
        console.log("进入autoNLP函数")
        useContentOfTextarea = true;
        if(fileth == theFiles.length){
            alert("congratulation! the task has been finished!")
            return -1;
        }
        if(fileth < theFiles.length){
            console.log("清除上个文件的残留标签")
            let leftParent = document.getElementsByClassName('leftOne')
            let rightParent = document.getElementsByClassName('rightOne')
            removeAllLabels(leftParent)
            removeAllLabels(rightParent)
            console.log("同步内容到文本框")
            let reader = new FileReader
            let textAera = document.querySelector('#textField')
            reader.readAsText(theFiles[fileth],'utf-8')
            reader.onload = function (event) {
            var data = event.target.result;
            // console.log(event)
            // console.log(event.target);
            // console.log("data is" + data)
            textAera.innerHTML = data
            }

            console.log("分析文件" + fileth)
            let formdata = new FormData;
            formdata.append('uploadFile',theFiles[fileth])
            console.log(formdata.get('uploadFile'))
            let url = 'http://localhost:8080/uploadFile'; // todo:根据你的文件来确定url

            jQuery.ajax({
                type: 'post',
                url: url,
                data: formdata,
                contentType: false,
                processData: false,
                success: function (data) {
                    // alert("success");
                    console.log(data)
                    words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
                    formLabels(words)
                    afterAnalyse()
                }
            })
            return fileth;
        }

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



    // 实现点击上传按钮，将文本内容同步到输入框，同时保存提交的文件等待分析
    function upLoadFile() {
        let file = document.getElementById("submitLocalFile")
        let textAera = document.querySelector('#textField')
        // 实现将文件的内容显示在文本框内
        let reader = new FileReader
        console.log('提交按钮是')
        console.log(this.file)
        console.log('提交的所有文件是')
        console.log(file.files)
        console.log('提交的文件是')
        console.log(file.files[0])

        theFiles = file.files; // 将提交的多个文件赋给theFiles

        reader.readAsText(file.files[0],'utf-8')
        reader.onload = function (event) {
            var data = event.target.result;
            // console.log(event)
            // console.log(event.target);
            // console.log("data is" + data)
            textAera.innerHTML = data
        }


    }

    // parent : HTMLCollection类
    // 删除parent下的所有子标签
    function removeAllLabels(parent){
         for (let i = 0; i < parent.length; i++) {
             const curParent = parent[i];
             curParent.innerHTML = ""
         }
    }
