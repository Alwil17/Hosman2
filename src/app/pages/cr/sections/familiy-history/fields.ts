import { Section } from "../../field.model";

export const fields: Section[] = [
  {
    template: [
      {
        name: "rang_label",
        label: "Rang dans la fratrie",
        type: "label",
        col: 4,
        show: true,
      },
      {
        name: "rang",
        type: "select",
        options: [
          { id: 1, text: "Unique" },
          { id: 2, text: "Deuxieme" },
          { id: 3, text: "Troisieme" },
          { id: 4, text: "Quatrieme" },
          { id: 5, text: "Cinquieme" },
          { id: 6, text: "Sixieme" },
          { id: 7, text: "Septieme" },
          { id: 8, text: "Huitieme" },
          { id: 9, text: "Neuvieme" },
          { id: 10, text: "Dixieme" },
        ],
        col: 8,
        show: true,
        conditions: [
          {
            eval: "v === 'Unique'",
            text: "C'est un enfant unique du pere",
          },
          {
            eval: "v !== 'Unique'",
            text: "C'est le %v% enfant d'une fratrie de {{ effectif }}",
          },
        ],
      },
      {
        name: "efff_label",
        label: "Effectif de la fratrie",
        type: "label",
        col: 4,
        show: true,
      },
      {
        name: "effectif",
        type: "input",
        col: 8,
        show: true,
      },
    ],
    resume: "{{ rang }}.",
  },
  {
    template: [
      {
        name: "age_pere",
        label: "Age du pere",
        type: "label",
        col: 4,
        show: true,
      },
      {
        name: "age",
        type: "input",
        col: 8,
        show: true,
      },
      {
        name: "prof_pere",
        label: "Profession du pere",
        type: "label",
        col: 4,
        show: true,
      },
      {
        name: "profession",
        type: "input",
        col: 8,
        show: true,
      },
    ],
    resume: "Le père agé de {{ age }} ans est {{ profession }}.",
  },

  {
    template:[
      {
        name: 'famhist_comments',
        label: "Informations supplémentaires",
        type: 'textarea',
        col: 12,
        show:true,
      }
    ],
    resume: "{{ famhist_comments }}"
  }
];
