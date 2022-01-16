
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
        let content = textField.value // content即为输入框中的文字，类型为字符串
        let url = "http://localhost:8080/getResult?text=" + content
        jQuery.ajax({
            type: 'post',
            url: url,
            // data: content,
            contentType: false,
            processData: false,
            success: function (data) {
                alert("success");
                words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
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


    /*
        const element = currentArr[index];
            var checkBox = document.createElement('input');
            checkBox.setAttribute('type','checkbox');
            checkBox.setAttribute('value',element); // 设置复选框属性
            var content = document.createTextNode(element); // 得到复选框后的文本
            var boxAndConetent = document.createElement('div'); // 创建包含复选框以及文本的小盒子
            boxAndConetent.setAttribute('class','boxAndConetent');
            boxAndConetent.appendChild(checkBox).appendChild(content); // 直接给当前盒子加上复选框和文本
            
            // 先放在左边再放到右边
            if(index % 2 == 0){
               var putedBox = document.querySelector('#noun > .leftOne');
            }else{
               var putedBox = document.querySelector('#noun > .rightOne');
            }
            putedBox.appendChild(boxAndConetent); // 把小盒子放到左盒子或右盒子
            // var currentBoxs = document.querySelectorAll('.boxAndConetent');
            // console.log(currentBoxs)
            // var currentBox = currentBoxs[currentBoxs.length-1]; // 得到最新加入的盒子
            
            // currentBox.appendChild(checkBox).appendChild(content); // 给其加上复选框和文本
            console.log(boxAndConetent)
            // checkBox.innerHTML = element[index];
            // noun.appendChild(checkBox);
            // noun.appendChild(content); 
     */