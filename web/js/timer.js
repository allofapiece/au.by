function elementTimer(element, timePoint, straightly = false, endFunc = function(){}) {
    var x = setInterval(function() {
        var timerResult;
        var time;

        timePoint instanceof Date ? time = timePoint.getTime() : time = new Date(timePoint).getTime();

        var now = new Date().getTime();

        var distance = straightly ? now - time : time - now;

        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        if (days > 0) {
            timerResult = time;
        } else {
            var hoursString = hours > 0 ? hours + ":" : '';
            var minutesString = minutes > 0 ? minutes + ":" : '';

            timerResult = hoursString + minutesString + seconds
        }

        element.text(timerResult);

        if (distance < 0) {
            clearInterval(x);
            endFunc();
        }
    }, 1000);
}