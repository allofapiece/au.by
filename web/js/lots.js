var classPrefix = 'lot';

$(document).ready(function () {
    $('#add-lot-form #start-date-field').val(new Date().toDateInputValue());

    if (qs('command') === 'lot-add' && qs('productid') !== null) {
        addProduct(qs('productid'));
    }

    loadLots();
});

function showLots(lots) {
    var currentLots = $('.lot');

    deleteExcess(lots, currentLots);
    addNew(lots, currentLots);
}

function deleteExcess(newLots, currentLots) {
    var isFind = false;
    currentLots.each(function () {
        var lotId = $(this).find('input[name="lot-id"]').val();
        isFind = false;

        newLots.forEach(function (lot) {
            if (lot.id === parseInt(lotId)) {
                isFind = true;
                return false;
            }
        });

        if (!isFind && !$(this).hasClass('prototype')) {
            this.remove();
        }
    });
}

function addNew(newLots, currentLots) {
    var isFind = false;
    newLots.forEach(function (lot) {
        isFind = false;

        currentLots.each(function () {
            var lotId = $(this).find('input[name="lot-id"]').val();
            if (lot.id === parseInt(lotId)) {
                isFind = true;
                return false;
            }
        });

        if (!isFind) {
            showLot(lot);
        }
    });
}

function showLot(lot) {
    var newLotElement = getPrototype('.lots .prototype', ['lot-prototype', 'prototype']);
    var header = newLotElement.find('.card-header');
    var body = newLotElement.find('.card-body');
    var footers = newLotElement.find('.card-footer');

    footers.each(function () {
        if (!$(this).hasClass(classPrefix + '-' + lot.status.toLowerCase())) {
            $(this).remove();
        }
    });

    ejectLabels(newLotElement);
    loadSeller(lot.sellerId, newLotElement);
    init(newLotElement, lot);
    initFooters(footers, newLotElement, lot);

    switch (lot.status.toLowerCase()) {
        case 'open':
            addStyleClasses([newLotElement], ['border-cyan']);
            addStyleClasses([header], ['bg-cyan']);

            if (lot.sellerId !== userId) {
                newLotElement.find('.lot-action.action-cancel').remove();
            }

            break;

        case 'proposed':
            addStyleClasses([newLotElement], ['border-secondary']);
            addStyleClasses([header], ['bg-secondary']);
            break;

        case 'started':
            addStyleClasses([newLotElement], ['border-primary']);
            addStyleClasses([header], ['bg-primary']);
            break;

        case 'closed':
            addStyleClasses([newLotElement], ['border-danger']);
            addStyleClasses([header], ['bg-danger']);

            if (lot.message != null && lot.message !== '') {
                header.find('small').append(" (" + lot.message + ")");
            }
            break;

        case 'completed':
            addStyleClasses([newLotElement], ['border-teal']);
            addStyleClasses([header], ['bg-teal']);

            if (lot.sellerId !== userId) {
                newLotElement.find('.lot-action.action-take-winnings').remove();
            }

            break;
    }

    newLotElement.show();
    $('.lots').prepend(newLotElement);

}

function init(element, lot) {
    var headers = element.find('.card-header');
    var buttons = element.find('.btn.lot-action');
    headers.each(function () {
        if (!$(this).hasClass(classPrefix + '-' + lot.status.toLowerCase())) {
            this.remove();
        }
    });
    buttons.each(function () {
        var button = this;
        if ($(this).hasClass(classPrefix + '-' + lot.status.toLowerCase())) {
            if ($(this).hasClass('action-play')) {
                lot.bieters.forEach(function (bieter) {
                    if (bieter.userId === userId) {
                        $(button).remove();
                        return false;
                    }
                });
            }
            if ($(this).hasClass('action-play') && lot.sellerId === userId) {
                $(this).remove();
            }
            if (!userRoles.includes('admin')
                && ($(this).hasClass('action-reject') || $(this).hasClass('action-accept'))) {
                $(this).remove()
            }
            if ($(this).hasClass('action-take-back') && lot.sellerId !== userId) {
                $(this).remove()
            }
            if ($(this).hasClass('action-cancel') && lot.sellerId !== userId) {
                $(this).remove()
            }
            if ($(this).hasClass('action-take-winnings')) {
                $(this).remove();
            }
            var href = $(this).attr('href');
            href = href.replace(/id=#/g, "id=" + lot.id);
            $(this).attr('href', href);
        } else {
            $(this).remove();
        }
    });
    element.find('.' + classPrefix + '-' + lot.status.toLowerCase()).show();
    element.find('.card-title').append(lot.name);
    element.find('.lot-begin-price').append(lot.beginPrice);
    element.find('.lot-amount').append(lot.productAmount);
    element.find('.lot-description').append(lot.description);
    element.find('.lot-type').append(
        lot.auctionType.charAt(0) +
        lot.auctionType.toLowerCase().substr(1)
    );
    element.find('input[name="lot-id"]').val(lot.id);
    bindEvent(element, "click");
}

function initFooters(footers, lotElement, lot) {
    var indexes = [];
    footers.each(function (index, item) {
        if (!$(this).hasClass(classPrefix + '-' + lot.status.toLowerCase())) {
            this.remove();
            indexes.push(index);
        }
    });

    indexes.reverse().forEach(function (value) {
        footers.splice(value, 1);
    });

    var inscriptions = {"full": footers.data('label-full'), "short": footers.data('label-short')};

    switch (lot.status.toLowerCase()) {
        case 'open':
            addStyleClasses([footers], ['bg-transparent', 'border-cyan']);

            elementTimer(footers.find('.lot-timer'), lot.startTime, false, inscriptions, function() {
                lotElement.remove();
            });

            break;

        case 'proposed':
            addStyleClasses([footers], ['bg-transparent', 'border-secondary']);

            elementTimer(footers.find('.lot-timer'), lot.startTime, false, inscriptions, function() {
                lotElement.remove();
            });

            break;

        case 'started':
            addStyleClasses([footers], ['bg-transparent', 'border-primary']);

            elementTimer(footers.find('.lot-timer'), lot.startTime, true, inscriptions, function() {
                lotElement.remove();
            });

            break;

        case 'closed':
            addStyleClasses([footers], ['bg-transparent', 'border-danger']);

            elementTimer(footers.find('.lot-timer'), lot.endTime, true, inscriptions, function() {
                lotElement.remove();
            });

            break;

        case 'completed':
            addStyleClasses([footers], ['bg-transparent', 'border-teal']);

            elementTimer(footers.find('.lot-timer'), lot.endTime, true, inscriptions, function() {
                lotElement.remove();
            });

            break;
    }
}

function loadSeller(id, element) {
    success = function (data) {
        element.find('.lot-seller-name').append(data.reqAttrs.user.name);
    };
    loadUser(id, success, element);
}

function loadLots(data = null) {
    if (qs('command') === 'lot-show' && qs('scope') === 'mine') {
        loadMyLots(data);
    } else if (qs('command') === 'lot-show' && qs('scope') === 'all') {
        loadAllLots(data);
    }
}
function loadMyLots(data = null) {
    var success = function (data) {
        showLots(data.reqAttrs.lots);
    };
    ajaxAction('/fc?command=lot-load&scope=mine', success, data, 'GET', 'json');
}

function loadAllLots(data = null) {
    var success = function (data) {
        showLots(data.reqAttrs.lots);
    };
    ajaxAction('/fc?command=lot-load&scope=all', success, data, 'GET', 'json');
}

$(document).ready(function () {
   $('select[name="type"]').on("change", function () {
       $('.additional .form-group').hide();
       $('.additional h4').hide();

       $('.' + $(this).val()).show();
   });

   $('.lot-filter .btn').on('click', function () {
       $(this).toggleClass('active');

       var terms = [];
       $('.lot-filter .btn').each(function () {
           if ($(this).hasClass('active')) {
               terms.push($(this).data('filter-term'));
           }
       });

       loadLots({filter:terms.join(',')});
   });

   $('.lot-search a.search-button').on('click', function () {
       $('.lot-filter .btn').removeClass('active');
       searchLots($('.lot-search input').val());
   });

   $('.lot-products .delete-product').on('click', function () {
       $('.lot-products').hide();
       $('.lot-products .product input[name="product-id"]').val('');
   })
});

function searchLots(value) {
    $.ajax({
        method: 'post',
        data: {
            searchValue: value
        },
        url: '/fc?command=lot-search',
        success: function (data) {
            showLots(data.reqAttrs.lots);
        }
    })
}

function bindEvent(element, event) {
    element.on(event, function () {
        window.location.href = '/fc?command=lot-show-one&id=' + element.find('input[name="lot-id"]').val();
    });
}



