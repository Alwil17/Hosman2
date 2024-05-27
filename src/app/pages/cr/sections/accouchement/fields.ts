import { Section } from "../../field.model";

export const fields : Section[]  = [
  {
    template: [
      {
        name: "accouchement",
        label: "Accouchement par",
        type: "select",
        options: [
          { id: 1, text: "Voie basse" },
          { id: 2, text: "Cesarienne" },
          { id: 3, text: "Forceps" },
        ],
        col: 5,
        show: true,
      },
      {
        name: "cesarienne",
        label: "Cause de la cesarienne",
        type: "input",
        col: 5,
        if: [{ name: "accouchement", value: 2 }],
        conditions: [
          {
            eval: "v !== ''",
            text: "Les causes de la cesarienne sont : %v%",
          },
        ],
        show: true,
      },
      {
        name: "terme",
        label: "Terme",
        type: "select",
        options: [
          { id: 20 },
          { id: 21 },
          { id: 22 },
          { id: 23 },
          { id: 24 },
          { id: 25 },
          { id: 26 },
          { id: 27 },
          { id: 28 },
          { id: 29 },
          { id: 30 },
          { id: 31 },
          { id: 32 },
          { id: 33 },
          { id: 34 },
          { id: 35 },
          { id: 36 },
          { id: 37 },
          { id: 38 },
          { id: 39 },
          { id: 40 },
          { id: 41 },
          { id: 42 },
        ],
        bindlabel: "id",
        col: 2,
        show: true,
        conditions: [
          {
            eval: "v < 38",
            text: "prématuré de %v% semaines d'aménorhee",
          },
          {
            eval: "v >= 38",
            text: "de %v% semaines d'arménorhee",
          },
        ],
      },
    ],
    resume:
      "Accouchement par {{ accouchement }} d'un {{ patient.statut }} de sexe {{ patient.gender }} {{ terme }}. {{ cesarienne }}",
  },

  {
    template: [
      {
        name : 'incident',
        label : "Incident",
        type: "select",
        col : 4,
        show : true,
        options : [
          { id : 1 , text : 'Sans incident' },
          { id : 2 , text : 'Avec incident' }
        ],
      },
      {
        name : 'details_incident',
        label : "Details de l'incident",
        type: "input",
        col : 8,
        show : true,
        options : [],
        if: [{ name: "incident", value: 2 }],
        conditions: [
          {
            eval: "v !== ''",
            text: "A noter la survenue d'un incident au cours de l'accouchemet : %v%",
          },
        ],
      },
    ],
    resume : "{{ details_incident }}"
  }
];
