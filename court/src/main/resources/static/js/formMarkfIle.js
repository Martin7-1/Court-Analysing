

var ar = document.getElementById('differentPro');
var btns = ar.getElementsByTagName('input');
var labels = document.getElementsByClassName('markLabels')
var beginAnalyse = document.getElementById('beginAnalyse');
var currentPro = "none";
var fileth = 1; // 当前分析的文件的序号，用于点击保存按钮后调用autoNLP函数的参数
var toTheEnd = 0;// 判断fileth是否到达文件末尾，到达则赋值负1
var markObject = {
    '当事人 ':[],

    '性别 ':[],

    '民族 ':[],

    '出生地 ':[],

    '案由 ':[],

    '相关法院':[],

};

var rawMarkObject =  {
    '当事人 ':[],

    '性别 ':[],

    '民族 ':[],

    '出生地 ':[],

    '案由 ':[],

    '相关法院':[],

}; // 暂时存储使其之后多文件的情况下能够初始化

// 控制时序，在NLP分析得到新的标签后重新获取，绑定事件
function afterAnalyse() {
    // console.log("这是开始分析按钮绑定的第二个事件")
    labels = document.getElementsByClassName('markLabels')
    // console.log("分析之后的标签是")
    // console.log(labels)
    // 获取当前点击的标签，根据用户选择动态修改markObject对象内容
  for (let index = 0; index < labels.length; index++) {
    const currentLabel = labels[index];
    currentLabel.onclick = function (event) {
        var str;
        if(event.pointerId == 1){
            console.log("---------------------")
            // console.log("the event is ")
            // console.log(event)
            // console.log("the event target is" )
            // console.log(event.target)
            // console.log("the event target'value is" )
            // console.log(event.target.value)
            // console.log('the innerHTML is '+event.target.innerHTML)
            if (event.target.value) {
                str = event.target.value
            }else{
                str = removeHtml(event.target.innerHTML) // 这个str就是其对应的值    
            }
            console.log('currentPro:'+currentPro+';')
            console.log('currentLabel:' + str + ';')
            // 遍历对象，找到需要添加的数组
            for(var key in markObject){
                // console.log('currentKey:'+key+';currentPro:'+currentPro+';');
                // console.log('isEqual:'+(key == currentPro))
                if(key == currentPro){
                    let isDelete = false;
                    // 实现删除操作，即如果点击的标签已在markObject中说明是删除操作
                    for (let k in markObject) {
                        if (k == currentPro) {
                            if (isContain(markObject[k],str)) {
                                console.log('接下来进行删除操作')
                                console.log('the str is in ' + k + ' arr;');
                                isDelete = true;
                                let newArr = [];
                                for (let index = 0; index < markObject[k].length; index++) {
                                    const curLabel = markObject[k][index];
                                    if (curLabel != str) {
                                        newArr.push(curLabel);
                                    }
                                }
                                markObject[k] = newArr;
                                console.log('the updated Arr is:'+markObject[k])
                                console.log('the updated object is:')
                                console.log(markObject)    
                            }    
                        }
                        
                    }
                    if (!isDelete) {
                        console.log('接下来进行增添操作');
                        markObject[key].push(str);
                        console.log('the updated object is:')
                        console.log(markObject)    
                    }
                    
                }
                
            } 
        }
    }    
  }
}

  // 实现标注栏切换效果,根据markObject中的值，来确定各个标注下的标签的初始状态
  for (let i = 0; i < btns.length; i++) {
    console.log(btns[0].value);
    btns[i].onclick = function () {
        for (let index = 0; index < btns.length; index++) {
            const element = btns[index];
            element.style.borderBottomColor = '#61BFFF';
            
        }
        btns[i].style.borderBottomColor = 'red';
        currentPro = btns[i].value;
        var allLables = document.getElementsByClassName('theWords')
        // console.log('all the labels are:')
        // console.log(allLables);
        // 先全部清除
        for (let index = 0; index < allLables.length; index++) {
            const currentLab = allLables[index];
            currentLab.checked = false;
            
        }
        // 再根据markObject中的值进行恢复
        for (let index = 0; index < allLables.length; index++) {
            const currentLab = allLables[index];
            let curArr 
            for(let k in markObject){
                if (currentPro == k) {
                    curArr = markObject[k]; // 当前pro下对应的数组，根据其内数据恢复标签状态
                }
            }
            for(let i=0; i<curArr.length; i++){
                if (currentLab.value == curArr[i]) {
                    currentLab.checked = true
                    break;
                }
            }
        

            
            
        }
        // console.log('currentPro:' + currentPro + ';');
        
    }
  }

  
  function removeHtml(Htmlstring) {
    let index = 0
    for (let i = 0; i < Htmlstring.length; i++) {
        
        if (Htmlstring[i] == '>') {
            index = i+1
            break
        }
        
    }
    return Htmlstring.substring(index)

  }

  //判断arr数组中是否含有char元素，含有则返回true
  function isContain(arr,char) {
    for (let index = 0; index < arr.length; index++) {
        const element = arr[index];
        if(element == char) return true;
    }
    return false;
  }

  // 保存标注形成json文件
  var saveButton = document.getElementById('save')
  var textField = document.getElementById('textField')
  saveButton.onclick = function () {
    let content = JSON.stringify(markObject);
    var blob = new Blob([content], {type: "text/plain;charset=utf-8"});
    let textContent = JSON.stringify(textField.value)
    let textBlob = new Blob([textContent],{type: "text/plain;charset=utf-8"})
    console.log("当前标注已保存，清除标注信息")
    markObject = toRawMarkObject();
    console.log("清空结果为")
    console.log(markObject)
    if (toTheEnd != -1) {
        download('mark'+fileth+'.json',blob)
        download('content'+fileth+'.txt',textContent)
        
        console.log("当前保存的文件是:file" + (fileth-1))
        console.log("接下来分析的文件是" + fileth)
        toTheEnd = autoNLP(fileth)
        fileth = fileth + 1;    
    }
  }

  function fake_click(obj) {
    var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent(
        "click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null
    );
    obj.dispatchEvent(ev);
  }

  function download(name, data) {
    var urlObject = window.URL || window.webkitURL || window;

    var downloadData = new Blob([data]);

    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(downloadData);
    save_link.download = name;
    fake_click(save_link);
  }

  function toRawMarkObject() {
      let oriMarkObject = {
        '当事人 ':[],
    
        '性别 ':[],
    
        '民族 ':[],
    
        '出生地 ':[],
    
        '案由 ':[],
    
        '相关法院':[],
    
    }  
    return oriMarkObject;
  }

    