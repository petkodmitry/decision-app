function isNumeric(elem) {
    event.preventDefault();
    n = elem.value;
    if (!/^-?\d*$/.test(n)) {
        alert("value should be a natural number");
        elem.value = '';
        return false;
    } else {
    }
    return true;
}

function isDecimal(elem) {
    event.preventDefault();
    n = elem.value;
    if (!/^-?\d*[\.\,]?\d*?$/.test(n)) {
        alert("value should be a number");
        elem.value = '';
        return false;
    }
    return true;
}
