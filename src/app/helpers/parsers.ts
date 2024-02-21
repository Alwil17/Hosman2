/**
 * Tries to parse `value` to an integer
 * @param value A variable of any type
 * @returns `0` if the value is not a valid number or an integer
 */
export function parseIntOrZero(value: any) {
  value = String(value);

  return Number.isNaN(parseInt(value)) ? 0 : parseInt(value);
}

/**
 * Tries to parse `value` to a float/decimal
 * @param value A variable of any type
 * @returns `0.0` if the value is not a valid number or a float/decimal
 */
export function parseFloatOrZero(value: any, decimalLength: number = 2) {
  value = String(value);

  let parsedValue = Number.isNaN(parseFloat(value)) ? 0.0 : parseFloat(value);

  let slicedValue = parsedValue.toFixed(decimalLength);

  parsedValue = parseFloat(slicedValue);

  return parsedValue;
}
