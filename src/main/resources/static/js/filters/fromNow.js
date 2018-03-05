'use strict';

/* Filters */
// need load the moment.js to use this filter. 
app.filter("fromNow", function () {
    return function (date) {
        return moment(date).fromNow();
    }
})
.filter('datetime', function () {
    return function (date) {
        if (parseInt(date) > 1400000) {
            date = (date + '000') * 1;
        }
        return moment(date).format('YYYY-MM-DD HH:mm:ss');
    };
})
.filter('dateT', function () {
    return function (date) {
        if (parseInt(date) > 1400000) {
            date = (date + '000') * 1;
        }
        return moment(date).format('YYYY-MM-DD');
    };
})
.filter('dateSecondTime', function () {
    return function (date) {
        return moment(date).format('YYYY-MM-DD HH:mm:ss');
    };
});