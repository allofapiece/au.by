var classPrefix = 'lot';

$(document).ready(function () {
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
    var footer = newLotElement.find('.card-footer');

    loadSeller(lot.sellerId, newLotElement);

    ejectLabels(newLotElement);
    init(newLotElement, lot);

    switch (lot.status.toLowerCase()) {
        case 'open':
            addStyleClasses([newLotElement], ['border-cyan']);
            addStyleClasses([header], ['bg-cyan']);
            addStyleClasses([footer], ['bg-transparent', 'border-cyan']);

            if (lot.sellerId !== userId) {
                newLotElement.find('.lot-action.action-cancel').remove();
            }

            break;

        case 'proposed':
            addStyleClasses([newLotElement], ['border-secondary']);
            addStyleClasses([header], ['bg-secondary']);
            addStyleClasses([footer], ['bg-transparent', 'border-secondary']);
            break;

        case 'started':
            addStyleClasses([newLotElement], ['border-primary']);
            addStyleClasses([header], ['bg-primary']);
            addStyleClasses([footer], ['bg-transparent', 'border-primary']);
            break;

        case 'closed':
            addStyleClasses([newLotElement], ['border-danger']);
            addStyleClasses([header], ['bg-danger']);
            addStyleClasses([footer], ['bg-transparent', 'border-danger']);
            break;

        case 'completed':
            addStyleClasses([newLotElement], ['border-teal']);
            addStyleClasses([header], ['bg-teal']);
            addStyleClasses([footer], ['bg-transparent', 'border-teal']);

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
        if (!$(this).hasClass(classPrefix + '-' + lot.status.toLowerCase())) {
            $(this).remove();
        } else {
            var href = $(this).attr('href');
            href = href.replace(/id=#/g, "id=" + lot.id);
            $(this).attr('href', href);
        }
    });
    element.find('.' + classPrefix + '-' + lot.status.toLowerCase()).show();
    element.find('.card-title').append(lot.name);
    element.find('.lot-begin-price').append(lot.beginPrice);
    element.find('.lot-description').append(lot.description);
    element.find('.lot-type').append(
        lot.auctionType.charAt(0) +
        lot.auctionType.toLowerCase().substr(1)
    );
    element.find('input[name="lot-id"]').val(lot.id);
    bindEvent(element, "click");
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
});

function bindEvent(element, event) {
    element.on(event, function () {
        window.location.href = '/fc?command=lot-show-one&id=' + element.find('input[name="lot-id"]').val();
    });
}



