var searchResultSection = $('.search .search-result');

function qs(key) {
    key = key.replace(/[*+?^$.\[\]{}()|\\\/]/g, "\\$&"); // escape RegEx meta chars
    var match = location.search.match(new RegExp("[?&]" + key + "=([^&]+)(&|$)"));

    return match && decodeURIComponent(match[1].replace(/\+/g, " "));
}

Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

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
            if (data.reqAttrs.products.length !== 0) {
                data.reqAttrs.products.forEach(function (product) {
                    var prototype = '<li data-product="' + product.id + '" ' +
                        '>' +
                        product.name +
                        '</li>';
                    $('.search .search-result').append(prototype);
                });
            }
            $('.search .search-result').show();
            updateEvents();
        }
    })
}

function updateEvents() {
    $('.search .search-result').find('li').off('click').on('click', function () {
        addProduct($(this).data('product'));
    });
}

function addProduct(id) {
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
    /*$('#signup-form input').on('input', function() {
        if (!this.checkValidity()) {
            $('button[form="signup-form"]').click();
        }
    });*/
    $('#product-id-field').on("input", function () {
        $('.search .search-result').empty().hide();
        var value = $(this).val();
        if (value.length > 0) {
            searchProduct(value);
        }
    });

    $('body').on('click', function (e) {
        if (e.target.id !== 'product' && e.target.id !== 'product-id-field') {
            $('.search .search-result').hide();
        }
    });

    $('#product-id-field').on('focusin', function () {
        $('.search .search-result').show();
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

function ejectLabels(element) {
    var targets = element.find(
        'p[data-label],' +
        'h1[data-label],' +
        'h2[data-label],' +
        'h3[data-label],' +
        'h4[data-label],' +
        'h5[data-label],' +
        'button[data-label],' +
        'div[data-label]' +
        'a[data-label]'
    );
    targets.each(function () {
        if (
            $(this).is('p') ||
            $(this).is('h1') ||
            $(this).is('h2') ||
            $(this).is('h3') ||
            $(this).is('h4') ||
            $(this).is('h5') ||
            $(this).is('a') ||
            $(this).is('div')
        ) {
            $(this).prepend('<strong>' + $(this).data('label') + ':</strong> ');
        } else if ($(this).is('button')) {
            this.val(this.data('label'));
        }
    });
}

function deleteExcessEntities(newObjects, currentObjects) {
    var isFind = false;
    currentObjects.each(function () {
        var id = $(this).find('input[name="entity-id"]').val();
        isFind = false;

        newObjects.forEach(function (entity) {
            if (entity.id === parseInt(id)) {
                isFind = true;
                return false;
            }
        });

        if (!isFind && !$(this).hasClass('prototype')) {
            this.remove();
        }
    });
}

function addNewEntities(newObjects, currentObjects, entityType) {
    var isFind = false;
    newObjects.forEach(function (entity) {
        isFind = false;

        currentObjects.each(function () {
            var id = $(this).find('input[name="entity-id"]').val();
            if (entity.id === parseInt(id)) {
                isFind = true;
                return false;
            }
        });

        if (!isFind) {
            displayEntity(entity, entityType);
        }
    });
}

function initEntity(element, entity) {
    element.find('input[name="entity-id"]').val(entity.id);
}

function displayEntity(entity, entityType) {
    var functionName = 'display' + capitalize(entityType);
    window[functionName](entity);
}

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.substr(1).toLowerCase();
}

function getRolesByJSPString(string = '') {
    return arrayToLowerCase(string.match(/([\w]+\b)/g));
}

function arrayToLowerCase(array) {
    for (var i = 0; i < array.length; i++) {
        array[i] = array[i].toLowerCase()
    }
    return array;
}