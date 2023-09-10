/**
 * Tries to parse `value` to an integer
 * @param value A variable of any type
 * @returns `0` if the value is not a valid number or an integer
 */
export function parseIntOrZero(value: any) {
  return Number.isNaN(parseInt(value)) ? 0 : parseInt(value);
}
