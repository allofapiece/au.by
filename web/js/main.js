function deleteProductAjax(id, product, success) {
    ajaxAction('/fc?command=product-delete', success, {id: id}, 'post');
}

function ajaxAction(url, success, data = null, method = 'POST', dataType = 'html') {
    $.ajax({
        method: method,
        url: url,
        data: data,
        dataType: 'json',
        success: success,
    });
}