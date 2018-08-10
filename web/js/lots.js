var classPrefix = 'lot';

$(document).ready(function () {
    if (qs('command') === 'lot-show' && qs('scope') === 'mine') {
        loadMyLots();
    } else if (qs('command') === 'lot-show' && qs('scope') === 'all') {
        loadAllLots();
    }
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
}

function loadSeller(id, element) {
    success = function (data) {
        element.find('.lot-seller-name').append(data.reqAttrs.user.name);
    };
    loadUser(id, success, element);
}

function ejectLabels(element) {
    var targets = element.find(
        'p[data-label],' +
        'h1[data-label],' +
        'h2[data-label],' +
        'h3[data-label],' +
        'h4[data-label],' +
        'h5[data-label],' +
        'div[data-label],' +
        'button[data-label],' +
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
            $(this).html('<strong>' + $(this).data('label') + ':</strong> ');
        } else if ($(this).is('button')) {
            this.val(this.data('label'));
        }
    });
}

function loadMyLots() {
    var success = function (data) {
        showLots(data.reqAttrs.lots);
    };
    ajaxAction('/fc?command=lot-load&scope=mine', success, null, 'GET', 'json');
}

function loadAllLots() {
    var success = function (data) {
        showLots(data.reqAttrs.lots);
    };
    ajaxAction('/fc?command=lot-load&scope=all', success, null, 'GET', 'json');
}

$(document).ready(function () {
   $('select[name="type"]').on("change", function () {
       $('.additional .form-group').hide();
       $('.additional h4').hide();

       $('.' + $(this).val()).show();
   });
});



