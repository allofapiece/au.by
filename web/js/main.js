var searchResultSection = $('.search .search-result');

function qs(key) {
    key = key.replace(/[*+?^$.\[\]{}()|\\\/]/g, "\\$&"); // escape RegEx meta chars
    var match = location.search.match(new RegExp("[?&]"+key+"=([^&]+)(&|$)"));
    return match && decodeURIComponent(match[1].replace(/\+/g, " "));
}

function loadUser(id, success, element) {
    $.ajax({
        method: 'post',
        data: {
            id: id,
        },
        url: '/fc?command=user-load&scope=one',
        success: success
    })
}

function searchProduct(value) {
    $.ajax({
        method: 'post',
        data: {
            searchValue: value
        },
        url: '/fc?command=product-search',
        success: function (data) {
            if (data.reqAttrs.products.length === 0) {
                //searchResultSection.append('<li>No Listings Found...</li>');
            } else {

                data.reqAttrs.products.forEach(function (product) {
                    var prototype = '<li data-product="' + product.id + '" ' +
                        '>' +
                        product.name +
                        '</li>';
                    searchResultSection.append(prototype);});}/*addClass([searchResultSection], 'd-block');
               removeClass([searchResultSection], 'd-none');*/
            searchResultSection.show();
            updateEvents();
        }
    })
}

function updateEvents() {
    searchResultSection.find('li').off('click').on('click', function () {
        addProduct.apply(this);
    });
}

function addProduct() {
    var that = $(this);
    var id = that.data('product');
    $.ajax({
        method: 'post',
        data: {id: id},
        url: '/fc?command=product-add-ajax',
        success: function (data) {
            var product = data.reqAttrs.product;
            var productElement = $('.lot-products .product');
            var amountElement = productElement.find('.product-amount');
            var priceElement = productElement.find('.product-price');

            productElement.find('.product-name').html(product.name);
            amountElement.html(amountElement.data('label') + ': ' + product.amount);
            priceElement.html(priceElement.data('label') + ': ' + product.price);
            productElement.find('.product-description').html(product.description);
            productElement.find('input[name="product-id"]').val(product.id);

            $('.lot-products').show();
            fillEmptyFields(product);
        }
    })
}

function fillEmptyFields(product) {
    var nameField = $('input[name="name"]');
    var descriptionField = $('input[name="description"]');
    var amountField = $('input[name="amount"]');
    var priceField = $('input[name="begin-price"]');

    nameField.val(nameField.val() !== '' ? nameField.val() : product.name);
    descriptionField.val(descriptionField.val() !== '' ? descriptionField.val() : product.description);
    amountField.val(amountField.val() !== '' ? amountField.val() : product.amount);
    priceField.val(priceField.val() !== '' ? priceField.val() : product.price);
}

$(document).ready(function () {
    $('#product-id-field').on("input", function () {
        searchResultSection.empty().hide();
        var value = $(this).val();
        if (value.length > 0) {
            searchProduct(value);
        }
    });

    $('body').on('click', function (e) {
        if (e.target.id !== 'product' && e.target.id !== 'product-id-field') {
            searchResultSection.hide();
        }
    });

    $('#product-id-field').on('focusin', function () {
        searchResultSection.show();
    });
});

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

function addStyleClasses(elements, classes) {
    elements.forEach(function (element) {
        classes.forEach(function (styleClass) {
            element.addClass(styleClass);
        });
    });
}

function getPrototype(selector, removeClasses) {
    var clone = $(selector).clone();

    if (removeClasses !== undefined && removeClasses.length !== 0) {
        removeClasses.forEach(function (removedClass) {
            clone.removeClass(removedClass)
        });
    }

    return clone;
}