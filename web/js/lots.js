$(document).ready(function () {
    if (location.href === 'http://localhost:8080/jsp/lot/showmine.jsp') {
        loadMyLots();
    } else if (location.href === 'http://localhost:8080/jsp/lot/show.jsp') {
        loadAllLots();
    }
});

function showMyLots(lots) {

}

function loadMyLots() {
    var success = function (data) {
        showMyLots(data.reqAttrs.lots);
    };
    ajaxAction('/fc?command=lot-show', success, null, 'GET', 'json');
}

$(document).ready(function () {
   $('select[name="type"]').on("change", function () {
       $('.additional .form-group').hide();
       $('.additional h4').hide();

       $('.' + $(this).val()).show();
   });
});