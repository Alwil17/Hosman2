import { Section } from "../../field.model";

export const fields : Section[]  = [
    {
        template: [
            {
                name: 'age_mere_label',
                label: "Age de la mere",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'age_mere',
                type: "input",
                col: 4,
                show: true,
            },
            {
                col:4,
                show:true
            },
            {
                name: 'profession_mere_label',
                label: "Profession de la mere",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'profession_mere',
                type: "input",
                col: 4,
                show: true,
            },
            {
                col:4,
                show:true
            },
            {
                name: "gestity_label",
                label: "Gestité",
                type: "label",
                col: 4,
                show: true,
            },
            {
                name: "gestity",
                type: "select",
                options: [
                    { id: 1},
                    { id: 2},
                    { id: 3},
                    { id: 4},
                    { id: 5},
                    { id: 6},
                    { id: 7},
                    { id: 8},
                    { id: 9},
                    { id: 10},
                    { id: 11},
                    { id: 12},
                    { id: 13},
                    { id: 14},
                    { id: 15},
                    { id: 16},
                    { id: 17},
                    { id: 18},
                    { id: 19},
                    { id: 20},
                ],
                bindlabel: 'id',
                col: 4,
                show: true,
            },
            {
                col:4,
                show:true
            },
            {
                name: "parity_label",
                label: "Parité",
                type: "label",
                col: 4,
                show: true,
            },
            {
                name: "parity",
                type: "select",
                options: [
                    { id: 1},
                    { id: 2},
                    { id: 3},
                    { id: 4},
                    { id: 5},
                    { id: 6},
                    { id: 7},
                    { id: 8},
                    { id: 9},
                    { id: 10},
                    { id: 11},
                    { id: 12},
                    { id: 13},
                    { id: 14},
                    { id: 15},
                    { id: 16},
                    { id: 17},
                    { id: 18},
                    { id: 19},
                    { id: 20},
                ],
                bindlabel: 'id',
                col: 4,
                show: true,
            },
            {
                col:4,
                show:true
            },
            {
                name: "cp_label",
                label: "Consultation prenatale",
                type: "label",
                col: 4,
                show: true,
            },
            {
                name: "cp",
                type: "select",
                options: [
                    { id: 1, text: "Oui"},
                    { id: 2, text: "Non"},
                ],
                conditions: [
                    {
                        eval: "v !== 2",
                        text: "Elle n'a fait aucune consultation prenatale.",
                    },
                ],
                col: 4,
                show: true,
            },
            {
                col:4,
                show:true
            },
            {
                name: "follow_at_l",
                label: "La grossesse a été suivie a ",
                type: "label",
                col: 4,
                show: true,
                if: [{ name: "cp", value: 1 }],
            },
            {
                name: 'follow_at',
                type: "input",
                col: 4,
                show: true,
                if: [{ name: "cp", value: 1 }],
            },
            {
                name: "follow_by_l",
                label: "Par Dr. ",
                type: "label",
                col: 1,
                show: true,
                if: [{ name: "cp", value: 1 }],
            },
            {
                name: 'follow_by',
                type: "input",
                col: 3,
                show: true,
                if: [{ name: "cp", value: 1 }],
            },
            {
                name: "cp_bn_l",
                label: "Nombre de CPN ",
                type: "label",
                col: 4,
                show: true,
                if: [{ name: "cp", value: 1 }],
            },
            {
                name: 'cp_bn',
                type: "select",
                options: [
                    { id: 1},
                    { id: 2},
                    { id: 3},
                    { id: 4},
                    { id: 5},
                    { id: 6},
                    { id: 7},
                    { id: 8},
                    { id: 9},
                    { id: 10},
                    { id: 11},
                    { id: 12},
                    { id: 13},
                    { id: 14},
                    { id: 15},
                    { id: 16},
                    { id: 17},
                    { id: 18},
                    { id: 19},
                    { id: 20},
                    { id: 21},
                    { id: 22},
                    { id: 23},
                    { id: 24},
                ],
                bindlabel: 'id',
                col: 4,
                show: true,
                if: [{ name: "cp", value: 1 }],
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Elle a fait %v% consultation(s) prenatale(s). La grossesse a été suivie a {{ follow_at }} par Dr {{ follow_by }}",
                    },
                ],
            },
            {
                col:4,
                show:true
            },
        ],
        resume: "La mère agée de {{ age_mere }} ans est {{ profession_mere }}. Gestité {{ gestity }}, Parité {{ parity }}. {{ cp_bn }}. "
    },
    {
        template : [
            {
                name : 'incident',
                label : "Incident au cours de la grossesse",
                type: "radio",
                col : 4,
                show : true,
                default : 1,
                options : [
                    { id : 1 , text : 'Sans incident' },
                    { id : 2 , text : 'Avec incident' }
                ],
                conditions: [
                    {
                        eval: "v === 1",
                        text: "Il n'y a pas eu d'incident au cours de la grossesse.",
                    },
                    {
                        eval: "v === 2",
                        text: "Il y a eu un incident au cours de la grossesse : {{ details_incident }}.",
                    },
                ],
            },
            {
                name : 'details_incident',
                label : "Details de l'incident",
                type: "textarea",
                col : 8,
                show : true,
                options : [],
                if: [{ name: "incident", value: 2 }],
            },
        ],
        resume : "{{ incident }}"
    },
    {
        template : [
            {
                name : 'bilan_pn',
                label : "Bilan prénatal",
                type: "radio",
                col : 4,
                show : true,
                default : 1,
                options : [
                    { id : 1 , text : 'Normal' },
                    { id : 2 , text : 'Pathologique' }
                ],
                conditions: [
                    {
                        eval: "v === 1",
                        text: "Le bilan prenatal est normal.",
                    },
                    {
                        eval: "v === 2",
                        text: "Le bilan prenatal est pathologique. {{ details_bilan }}",
                    },
                ],
            },
            {
                name : 'details_bilan',
                label : "Details du bilan",
                type: "textarea",
                col : 8,
                show : true,
                options : [],
                if: [{ name: "bilan_pn", value: 2 }],
            },
        ],
        resume : "{{ bilan_pn }}"
    },
    {
        template: [
            {
                name: "gm_l",
                label: "Grossesse multiple",
                type: "label",
                col: 4,
                show: true,
            },
            {
                name: 'gmultiple',
                type: "select",
                options: [
                    { id: 1, text: "Oui"},
                    { id: 2, text: "Non"},
                ],
                conditions: [
                    {
                        eval: "i === 1",
                        text: "Il s'agit d'une grossesse multiple ( {{ gmvalue }} )",
                    },
                ],
                col: 4,
                show: true,
            },
            {
                name: 'gmvalue',
                type: "select",
                options: [
                    { id: 1, text: "Gemellaire"},
                    { id: 2, text: "Triple"},
                    { id: 3, text: "Quadriple"},
                    { id: 4, text: "Quintuple"},
                    { id: 5, text: "Sextuple"},
                ],
                col: 4,
                show: true,
                if: [{ name: "gmultiple", value: 1 }],

            },
        ],
        resume : "{{ gmultiple }}"
    },
    {
        template : [
            {
                name: "hg_l",
                label: "Hospitalisation au cours de la grossesse",
                type: "label",
                col: 4,
                show: true,
            },
            {
                name: 'hg',
                type: "select",
                options: [
                    { id: 1, text: "Oui"},
                    { id: 2, text: "Non"},
                ],
                conditions: [
                    {
                        eval: "i === 1",
                        text: "La mere a été hospitalisé {{ nbh }} fois au cours de grossesse.",
                    },
                ],
                col: 2,
                show: true,
            },
            {
                name: "nbh_l",
                label: "Nombre",
                type: "label",
                col: 2,
                show: true,
                if: [{ name: "hg", value: 1 }],
            },
            {
                name: 'nbh',
                type: "select",
                options: [
                    { id: 1},
                    { id: 2},
                    { id: 3},
                    { id: 4},
                    { id: 5},
                ],
                bindlabel: 'id',
                if: [{ name: "hg", value: 1 }],
                col: 4,
                show: true,
            },
        ],
        resume : "{{ hg }}"
    },
    {
        name: 'hdetails',
        template:[],
        resume: "{{ hdetails }}"
    }
];