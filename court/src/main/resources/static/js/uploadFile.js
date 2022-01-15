var file = document.getElementById("submitLocalFile")
var textAera = document.querySelector('#textField')
function upLoadFile() {
    var file = document.getElementById("submitLocalFile")
    var reader = new FileReader
    console.log(file)
    console.log(file.files[0])
    
    reader.readAsText(file.files[0],'utf-8')
    reader.onload = function (event) {
        var data = event.target.result;
        // console.log(event)
        // console.log(event.target);
        // console.log("data is" + data)
        textAera.innerHTML = data
    }
}