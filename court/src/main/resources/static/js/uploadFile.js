//var file = document.getElementById("submitLocalFile")
//var textAera = document.querySelector('#textField')
//var theFile
//function upLoadFile() {
//    // 实现将文件的内容显示在文本框内
//    var reader = new FileReader
//    console.log('提交按钮是')
//    console.log(this.file)
//    console.log('提交的所有文件是')
//    console.log(file.files)
//    console.log('提交的文件是')
//    console.log(file.files[0])
//    theFile = file.files[0]
//
//
//    reader.readAsText(file.files[0],'utf-8')
//    reader.onload = function (event) {
//        var data = event.target.result;
//        // console.log(event)
//        // console.log(event.target);
//        // console.log("data is" + data)
//        textAera.innerHTML = data
//    }
//
//    // 实现直接将文件传输到8080端口
//    console.log(theFile)
//    let formdata = new FormData;
//    formdata.append('uploadFile',theFile)
//    console.log(formdata.get('uploadFile'))
//    let url = 'http://localhost:8080/uploadFile';
//
//    jQuery.ajax({
//        type: 'post',
//        url: url,
//        data: formdata,
//        contentType: false,
//        processData: false,
//        success: function (data) {
//            alert("success");
//            console.log(data)
//            words = data; // 我这边的words是一个对象形式，样例可见本文件10 - 14行
//        }
//    })
//}


