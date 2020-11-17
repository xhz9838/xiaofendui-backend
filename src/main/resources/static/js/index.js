function loadPageForSearch(keyword, path) {

    location.hash = path
    $("#directory").val(path)
    $.ajax({
        async: false,
        method: "get",
        url: "file/files?keyword=" + encodeURI(keyword) + "&path=" + path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        statusCode: {
            200: function (data) {
                var tableTbody = $("#tableTbody");
                tableTbody.html("")
                for (let i = 0; i < data.data.length; i++) {
                    let dataOf = data.data[i];
                    let size = renderSize(dataOf.length);
                    if (dataOf.type === "FILE") {
                        tableTbody.append(`<tr><td><a href="download?fileName=${dataOf.pathSuffix}&path=${location.hash.slice(1)}" style=\"cursor:pointer\" inode-type=\"${dataOf.type}\"  inode-path=\"${dataOf.pathSuffix}\">${dataOf.pathSuffix}</a></td><td>${dataOf.owner}</td><td>${dataOf.group}</td><td>${size}</td><td>文件</td><td>${dataOf.modificationTime}</td> <td> <button type="submit" class="btn btn-primary" id="deleteByFileName" onclick="deleteByFileName('${dataOf.pathSuffix}')">删除</button></td></tr>`)
                    } else {
                        tableTbody.append(`<tr><td><a style=\"cursor:pointer\" inode-type=\"${dataOf.type}\" class=\"explorer-browse-links\" inode-path=\"${dataOf.pathSuffix}\">${dataOf.pathSuffix}</a></td><td>${dataOf.owner}</td><td>${dataOf.group}</td><td>${size}</td><td>文件夹</td><td>${dataOf.modificationTime}</td> <td><button type="submit" inode-type=\"${dataOf.type}\" inode-path=\"${dataOf.pathSuffix}\" class="btn btn-primary" onclick="openDir('${dataOf.type}', '${dataOf.pathSuffix}')">打开</button></td></tr>`)
                    }
                }
            },
            500: function (data) {
                alert(data.msg)
            }
        }
    });


}

function openDir(type, path) {
    if (type === "DIRECTORY") {
        let dir = document.location.hash.slice(1);
        let filePath = append_path(dir, path);
        let keyword = $("#sousuokuang").val();
        loadPageForSearch(keyword, filePath);
    } else {
        //TODO
    }
}

function append_path(prefix, s) {
    var l = prefix.length;
    var p = l > 0 && prefix[l - 1] == '/' ? prefix.substring(0, l - 1) : prefix;
    return p + '/' + s;
}

function deleteByFileName(fileName) {

    let path = location.hash.slice(1);
    let pathFileName = append_path(path, fileName)

    if (confirm("删除后不可恢复,确认要删除改文件吗?")) {
        $.ajax({
            method: "delete",
            url: "file/delete?path=" + pathFileName,
            dataType: "json",
            success: function (response) {
                alert(response.msg)
                loadPageForSearch($("#sousuokuang").val(), path)
            },
            error: function (response) {
                alert(response.msg)
            }
        });
    }
}
function renderSize(value){
    if(null==value||value==''){
        return "0 Bytes";
    }
    var unitArr = new Array("Bytes","KB","MB","GB","TB","PB","EB","ZB","YB");
    var index=0;
    var srcsize = parseFloat(value);
    index=Math.floor(Math.log(srcsize)/Math.log(1024));
    var size =srcsize/Math.pow(1024,index);
    size=size.toFixed(2);//保留的小数位数
    return size+unitArr[index];
}
