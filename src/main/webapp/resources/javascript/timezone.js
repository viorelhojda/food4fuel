function setTimeZoneInCookie() {
    var _myDate = new Date();
    var _offset = _myDate.getTimezoneOffset();
    document.cookie = "TIMEZONE_COOKIE=" + _offset;
}