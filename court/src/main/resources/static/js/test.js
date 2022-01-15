var executerDiv=document.getElementById("executerDiv");
executerDiv.innerHTML="";
var ul=document.createElement("ul");
var tableDatas = [[1,'aa'],[2,'bb']]
for(var i=0;i<tableDatas.length;i++){
    var arr=tableDatas[i];

    // 加入复选框
    var checkBox=document.createElement("input");
    checkBox.setAttribute("type","checkbox");
    checkBox.setAttribute("id",arr[0]);
    checkBox.setAttribute("name", arr[1]);

    var li=document.createElement("li");
    li.appendChild(checkBox);       
    li.appendChild(document.createTextNode(arr[1]));
    li.setAttribute("style","list-style-type:none")
    ul.appendChild(li);       
}   

executerDiv.appendChild(ul);