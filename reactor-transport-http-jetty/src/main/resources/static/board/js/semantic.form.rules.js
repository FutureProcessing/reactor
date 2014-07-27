$.fn.form.settings.rules.isInteger = function(value) {
	return  /^\s*(\+|-)?\d+\s*$/.test(value);
}

$.fn.form.settings.rules.minValue = function(value, minValue) {
	var integerValue = parseInt(value, 10);
	return integerValue >= minValue;
}