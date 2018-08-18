var classPrefix = 'bet';
var members = [];

$(document).ready(function () {
    loadMembers({id: qs('id'), scope: 'lot'});
    loadBets({id: qs('id'), scope: 'lot'});

    setInterval(function() { loadMembers({id: qs('id'), scope: 'lot'}) }, 2000);
    setInterval(function() { loadBets({id: qs('id'), scope: 'lot'}) }, 1000);
});

function showBets(bets) {
    var currentBets = $('.bet');

    addNewEntities(bets, currentBets, 'bet');
}

function showMembers(members) {
    var currentMembers = $('.bieter:not(".prototype")');

    deleteExcessEntities(members, currentMembers);
    addNewEntities(members, currentMembers, 'member');
}

function displayBet(bet) {
    var newBetElement = getPrototype('.bets-list .prototype', ['bet-prototype', 'prototype']);

    loadBieter(bet.userId, newBetElement);

    initBet(newBetElement, bet);

    newBetElement.show();
    $('.bets-list').prepend(newBetElement);
}

function displayMember(member) {
    var newMemberElement = getPrototype('.members-list .prototype', ['bieter-prototype', 'prototype']);

    loadBieter(member.userId, newMemberElement);

    initMember(newMemberElement, member);

    newMemberElement.show();
    $('.members-list').prepend(newMemberElement);
}

function initMember(element, member) {
    initEntity(element, member);
}

function initBet(element, bet) {
    initEntity(element, bet);

    element.find('p.bet-price').text(bet.price + '$');
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

function loadBieter(id, element) {
    success = function (data) {
        var nameElement = element.find('.bet-bieter-name');

        nameElement.append(data.reqAttrs.user.name);
        if (data.reqAttrs.user.id === userId) {
            element.addClass('active');
            nameElement.append(' (' + nameElement.data('label') + ')');
        }
    };
    loadUser(id, success, element);
}
