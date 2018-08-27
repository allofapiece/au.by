'use strict';

function elementTimer(element, timePoint, straightly = false, inscriptions, endFunc = function(){}) {
    var x = setInterval(function() {
        var timerResult;
        var time;

        time = timePoint instanceof Date ? timePoint.getTime() : new Date(timePoint).getTime();

        var now = new Date().getTime();

        var distance = straightly ? now - time : time - now;

        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        if (days > 0) {
            timerResult = time;

            if (straightly) {
                clearInterval(x);
            }

            if (inscriptions !== undefined) {
                timerResult = inscriptions['full'].replace('{timer}', new Date(timePoint).toLocaleString());
            }
        } else {
            var hoursString = hours > 0 ? hours + ":" : '';
            var minutesString = (minutes < 10 ? ('0' + minutes) : minutes) + ":";
            var secondsString = seconds < 10 ? ('0' + seconds) : seconds;

            timerResult = hoursString + minutesString + secondsString;

            if (inscriptions !== undefined) {
                timerResult = inscriptions['short'].replace('{timer}', timerResult);
            }
        }

        element.text(timerResult);

        if (distance < 0) {
            clearInterval(x);
            endFunc();
        }
    }, 1000);
}