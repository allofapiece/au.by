$(document).ready(function () {
    $('.delete-product').on('click', function (e) {
        var id = $(this).find('input[name="delete-product"]').val();
        var product = $(this).closest('.product');
        deleteProductAjax(id, product, function (data) {
            if (data.errors !== undefined && data.errors.length !== 0) {
                alert("Error");
            }
            product.remove();
        })
    })
});