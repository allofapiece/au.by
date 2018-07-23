function deleteProductAjax(id, product, success) {
    ajaxAction('/fc?command=product-delete', {id: id}, 'post', success);
}

function ajaxAction(url, data, method, success) {
    $.ajax({
        method: method,
        url: url,
        data: data,
        dataType: 'json',
        success: success,
    });
}