$(document).ready(function () {
    $('.delete-product').on('click', function (e) {
        var id = $(this).find('input[name="delete-product"]').val();
        var product = $('input[name="delete-product"][value=' + id +']');
        deleteProductAjax(id, product, function (data) {
            if (data.errors !== undefined && data.errors.length !== 0) {
                alert("Error");
            }
            product.each(function () {
                $(this).closest('.product').remove();
            })
        })
    })
});