var classPrefix = 'bet';
var members = [];

$(document).ready(function () {
    loadMembers({id: qs('id'), scope: 'lot'});
    loadBets({id: qs('id'), scope: 'lot'});
});

function showBets(bets) {
    var currentBets = $('.bet');

    deleteExcessEntities(bets, currentBets);
    addNewEntities(bets, currentBets, 'bet');
}

function showMembers(members) {
    var currentMembers = $('.member');

    deleteExcessEntities(members, currentMembers);
    addNewEntities(members, currentMembers, 'member');
}

function displayBet(bet) {
    var newBetElement = getPrototype('.members', ['bet-prototype', 'prototype']);
}

function displayMember(member) {
    var newMemberElement = getPrototype('.members', ['bet-prototype', 'prototype']);

}

function init(element, lot) {
}

function loadMember(id, element) {
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

function loadBets(data = null) {
    if (qs('command') === 'lot-show-one') {
        loadLotBets(data);
    }
}

function loadLotBets(data = null) {
    var success = function (data) {
        showBets(data.reqAttrs.bets);
    };
    ajaxAction('/fc?command=bet-load', success, data, 'POST', 'json');
}

$(document).ready(function () {

});

function loadMembers(data = null) {
    var success = function (data) {
        showMembers(data.reqAttrs.bieters);
    };
    ajaxAction('/fc?command=bieter-load', success, data, 'POST', 'json');
}

