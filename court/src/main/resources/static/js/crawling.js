let crawlBut = document.getElementById('crawl')
crawlBut.onclick = function (params) {

    let searchText = document.getElementById('search')
    let textAera = document.getElementById('textField')
    let formdata = new FormData;
    formdata.append('searchContent',searchText.value)
    console.log('搜索框传输的关键字为: '+formdata.get('searchContent'))
    let url = 'http://localhost:8080/reptile'; //
    jQuery.ajax({
        type: 'post',
        url: url,
        data: formdata,
        contentType: false,
        processData: false,
        success: function (data) {
            alert('成功爬取')
            textAera.value = data
        }
    })
}