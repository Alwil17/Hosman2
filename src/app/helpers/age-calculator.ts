/**
 * Helper function to calculate the exact age from the date of birth
 * @param dateOfBirth A variable of type `Date` from which the calculation is made
 * @returns A string in the form : `x` an(s), `y` mois, `z` jour(s)
 */
export function calculateExactAge(dateOfBirth: Date) {
  const actualDate = new Date();

  const diffDay = actualDate.getDate() - dateOfBirth.getDate();
  const diffMonth = actualDate.getMonth() - dateOfBirth.getMonth();
  const diffYear = actualDate.getFullYear() - dateOfBirth.getFullYear();

  var day: number = 0;
  var month: number = 0;
  var year: number = 0;

  // Year calculation
  if (diffMonth > 0 || (diffMonth == 0 && diffDay >= 0)) {
    year = diffYear;
  } else if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
    year = diffYear - 1;
  }

  // Month calculation
  if (diffMonth > 0) {
    if (diffDay >= 0) {
      month = diffMonth;
    } else if (diffDay < 0) {
      month = diffMonth - 1;
    }
  } else if (diffMonth == 0) {
    if (diffDay >= 0) {
      month = 0;
    } else if (diffDay < 0) {
      month = 11;
    }
  } else if (diffMonth < 0) {
    if (diffDay >= 0) {
      month = 12 - Math.abs(diffMonth);
    } else if (diffDay < 0) {
      month = 11 - Math.abs(diffMonth);
    }
  }

  // Day calculation
  if (diffDay >= 0) {
    day = diffDay;
  } else if (diffDay < 0) {
    day = calculateMonthDaysCount(actualDate, true) - Math.abs(diffDay);
  }

//   console.log(day, month, year);

  return `${year} ${year < 2 ? " an" : " ans"}, ${month} mois, ${day} ${
    day < 2 ? " jour" : " jours"
  }`;
}

/**
 * A function to calculate the count of days in the month given by `date`. 
 * @param date A variable of type `Date` from which the calculation is made
 * @param previous If set to `true`, will make calculation based on the past month
 * @returns The count of days in the month based on the year given by `date`
 */
function calculateMonthDaysCount(date: Date, previous: boolean = false) {
  const tmp = new Date(date);
  if (previous) {
    tmp.setMonth(tmp.getMonth());
  } else {
    tmp.setMonth(tmp.getMonth() + 1);
  }
  tmp.setDate(0);
  return tmp.getDate();
}
