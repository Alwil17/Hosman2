export const WATCHES = [
  {
    name: "poids",
    label: "Poids",
    stepSize: 25,
    xPosition: 'top',
    type: 'chart'
  },
  {
    name: "temperature",
    label: "Température",
    stepSize: 25,
    xPosition: 'top',
    type: 'chart'
  },
  {
    name: "pouls",
    label: "Pouls",
    stepSize: 100,
    xPosition: 'top',
    type: 'chart'
  },  
  {
    name: "glycemie",
    label: "Glycèmie",
    stepSize: 2,
    xPosition: 'top',
    type: 'chart'
  },
  {
    name: "saturation",
    label: "Saturation",
    stepSize: 10,
    xPosition: 'top',
    type: 'chart'
  },
  {
    name: "t_art",
    label: "T.Artérielle",
    stepSize: 100,
    xPosition: 'top',
    type: 'value'
  },
  {
    name: "f_resp",
    label: "Fr. Respiratoire",
    stepSize: 40,
    xPosition: 'top',
    type: 'value'
  },
  {
    name: "f_card",
    label: "Fr. Cardiaque",
    stepSize: 10,
    xPosition: 'top',
    type: 'value'
  },
  {
    name: "urines",
    label: "Urines",
    stepSize: 100,
    xPosition: 'top',
    type: 'value'
  },
  {
    name: "selles",
    label: "Selles",
    stepSize: 100,
    xPosition: 'top',
    type: 'value',
    options: [
      "Non",
      "Oui, diarrhées",
      "Oui",
    ]
  },
  {
    name: "vomissements",
    label: "Vomissements",
    stepSize: 100,
    xPosition: 'top',
    type: 'value',
    options: [
      "Non",
      "Nausées",
      "Oui, alimentaires",
      "Oui, aqueux",
      "Oui, bilieux",
      "Oui, hémorragiques",
      "Oui, fécales",
    ]
  },
  {
    name: "gaz",
    label: "Gaz",
    stepSize: 100,
    xPosition: 'top',
    type: 'value',
  },
];
