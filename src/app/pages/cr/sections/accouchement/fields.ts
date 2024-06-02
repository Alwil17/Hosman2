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
        col: 3,
        show: true,
      },
      {
        name: "cesarienne",
        label: "Cause de la cesarienne",
        type: "input",
        col: 6,
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
        col: 3,
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
        type: "radio",
        col : 3,
        show : true,
        default : 1,
        options : [
          { id : 1 , text : 'Sans incident' },
          { id : 2 , text : 'Avec incident' }
        ],
        conditions : [
          {
            eval: "v === 1",
            text: "Il n'y a pas eu d'incident au cours de l'accouchement. L'enfant a crié de suite",
          },
        ]
      },
      {
        name : 'details_incident',
        label : "Details de l'incident",
        type: "textarea",
        col : 9,
        show : true,
        options : [],
        if: [{ name: "incident", value: 2 }],
        conditions: [
          {
            eval: "v !== ''",
            text: "A noter comme incident au cours de l'accouchement : %v%.",
          },
        ],
      },
    ],
    resume : "{{ incident }} {{ details_incident }}"
  },

  {
    template: [
      {
        name: 'apgar',
        label: "Apgar",
        type: 'label',
        col: 3,
        show:true,
      },
      {
        name: 'apgar_value_1',
        label: "",
        type: 'select',
        col: 2,
        options: [{id:1},{id:2},{id:3},{id:4},{id:5},{id:6},{id:7},{id:8},{id:9},{id:10},],
        bindlabel: "id",
        show:true,
      },
      {
        name: 'apgar_value_2',
        label: "",
        type: 'select',
        col: 2,
        options: [{id:1},{id:2},{id:3},{id:4},{id:5},{id:6},{id:7},{id:8},{id:9},{id:10},],
        bindlabel: "id",

        show:true,
      },
      {
        name: 'apgar_value_3',
        label: "",
        type: 'select',
        col: 2,
        options: [{id:1},{id:2},{id:3},{id:4},{id:5},{id:6},{id:7},{id:8},{id:9},{id:10},],
        bindlabel: "id",
        show:true,
      },
    ],
    resume: "L'apgar est a {{ apgar_value_1 }}, {{ apgar_value_2 }}, {{ apgar_value_3 }}."
  },

  {
    template: [
      {
        name: 'reanime',
        label: "Réanimé",
        type: 'checkbox',
        col: 3,
        default : false,
        conditions: [
          {
            eval: "v === false",
            text: "Il n'y a pas eu manoeuvre de réanimation.",
          },
          {
            eval: "v === true",
            text: "Le nouveau né a été réanimé.",
          },
        ],
        show:true,

      },
      {
        name: 'details_reanime',
        label: "Details de la réanimation",
        type: 'textarea',
        col: 9,
        show:true,
        if: [{ name: "reanime", value: true }],
      }
    ],
    resume: "{{ reanime }} {{ details_reanime }}"
  }
];
