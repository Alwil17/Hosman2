import {Section} from "../../../field.model";

export const paracliniqueFields: Section[] = [
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: "gbmm3_label",
                label: "Globule Blancs (/mm3)",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'gbmm3',
                type: "input",
                col: 4,
                show: true,
                errors: [
                    {
                        message: "La somme des globules doit atteindre 100%",
                        validator: "( parseInt({{ gbmm3pn }}) + parseInt({{ gbmm3l }}) + parseInt({{ gbmm3de }}) + parseInt({{ gbmm3mo }}) + parseInt({{ gbmm3ba }}) ) == 100"
                    }
                ],
                conditions : [
                    {
                        eval: "v !== null ",
                        text: "Les globules blancs sont de %v%/mm3 avec {{ gbmm3pn }}% de Polynucleaires, " +
                            "{{ gbmm3l }}% de lymphocytes, {{ gbmm3de }}% d'eosinophiles, {{ gbmm3mo }}% de monocytes,  {{ gbmm3ba }}% de basophiles. "
                    }
                ]
            },
            {
                name: "gbmm3_with",
                label: "avec",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'gbmm3_space',
                type: "space",
                col: 2,
                show: true,
            },
            {
                name: "gbmm3pn",
                label: "% de Polynucleaires neutrophiles",
                type: "input",
                col: 3,
                show: true,
            },
            {
                name: "gbmm3l",
                label: "% de lymphocytes",
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "gbmm3de",
                label: "% d'eosinophiles",
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "gbmm3mo",
                label: "% de monocytes",
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "gbmm3ba",
                label: "% de basophiles",
                type: "input",
                col: 2,
                show: true,
            },
        ],
        resume:  "{{ gbmm3 }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                type: 'line',
                col: 12,
                show: true,
            },
            {
                name: "hemogdl",
                label: "Hémoglobine (g/dl)",
                type: "input",
                col: 4,
                show: true,
                errors: [
                    {
                        message: "La valeur est comprise entre 1 et 12",
                        validator: "parseInt({{ hemogdl }}) >= 1 && parseInt({{ hemogdl }}) <= 12"
                    }
                ],
                conditions: [
                    {
                        eval: "v !== ''",
                        text: "L'Hémoglobine est de %v% avec {{ grmm3 }} et {{ plaqmm3 }}.",
                    },
                ],
            },
            {
                name: "grmm3",
                label: "Globules rouges (/mm3)",
                type: "input",
                col: 4,
                show: true,
                conditions: [
                    {
                        eval: "v.length <= 5",
                        text: "%v% millions de Globules rouges",
                    },
                    {
                        eval: "v.length > 5",
                        text: "%v% de Globules rouges",
                    },
                ],
            },
            {
                name: "plaqmm3",
                label: "Plaquettes (/mm3)",
                type: "input",
                col: 4,
                show: true,
                conditions: [
                    {
                        eval: "v.length <= 5",
                        text: "%v% millions de plaquettes",
                    },
                    {
                        eval: "v.length > 5",
                        text: "%v% de plaquettes",
                    },
                ],
            },
        ],
        resume: "{{ hemogdl }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                type: 'line',
                col: 12,
                show: true,
            },
            {
                name: "vs_l",
                label: "VS :",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'vs_v',
                type: "input",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(v) >= 1 && parseInt(v) <= 20",
                        text: "La VS est Normale a %v% mm a la premiere heure.",
                    },
                    {
                        eval: "parseInt(v) > 20 && parseInt(v) <= 30",
                        text: "La VS est Moderement elevée a %v% mm a la premiere heure.",
                    },
                    {
                        eval: "parseInt(v) > 30 && parseInt(v) <= 50",
                        text: "La VS est Elevée a %v% mm a la premiere heure.",
                    },
                    {
                        eval: "parseInt(v) >= 50",
                        text: "La VS est Tres Elevée a %v% mm a la premiere heure.",
                    },
                ],
            },
            {
                name: "al1mm_l",
                label: "mm",
                type: "label",
                col: 1,
                show: true,
            },
            {
                name: "al1h_space",
                type: "space",
                col: 6,
                show: true,
            },
            // {
            //     name: "al1h_l",
            //     label: "a la 1ere heure  ",
            //     type: "label",
            //     col: 3,
            //     show: true,
            // },
            // {
            //     name: 'al1h_v',
            //     type: "input",
            //     col: 2,
            //     show: true,
            //     // errors: [
            //     //     {
            //     //         message: "La valeur doit etre comprise entre 1 et 20",
            //     //         validator: "parseInt({{ vs_v }}) == 1 && parseInt({{ al1h_v }}) >= 1 && parseInt({{ al1h_v }}) <= 20"
            //     //     },
            //     //     {
            //     //         message: "La valeur doit etre comprise entre 21 et 30",
            //     //         validator: "parseInt({{ vs_v }}) == 2 && parseInt({{ al1h_v }}) > 20 && parseInt({{ al1h_v }}) > 20"
            //     //     },
            //     //     {
            //     //         message: "La valeur doit etre comprise entre 31 et 50",
            //     //         validator: "parseInt({{ vs_v }}) == 3 && parseInt({{ al1h_v }}) > 30 && parseInt({{ al1h_v }}) <= 50"
            //     //     },
            //     //     {
            //     //         message: "La valeur doit etre superieur a 50",
            //     //         validator: "parseInt({{ vs_v }}) == 4 && parseInt({{ al1h_v }}) > 50"
            //     //     }
            //     // ],
            // },
        ],
        resume: "{{ vs_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "crp_l",
                label: "La CRP est ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'crp_v',
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negative" },
                    { id: 2, text: "Positive" },
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) === 1",
                        text: "La CRP est Negative.",
                    },
                    {
                        eval: "parseInt(i) === 2",
                        text: "La CRP est Positive a {{ crpmg_v }} mg/l.",
                    },
                ],
            },
            {
                name: "crpa_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
                if: [{ name: "crp_v", value: 2 }],
            },
            {
                name: 'crpmg_v',
                type: "input",
                col: 2,
                show: true,
                if: [{ name: "crp_v", value: 2 }],
            },
            {
                name: "crpmg_l",
                label: "mg/l",
                type: "label",
                col: 2,
                show: true,
                if: [{ name: "crp_v", value: 2 }],
            },
            {
                name: "al1h_space",
                type: "space",
                col: 7,
                show: true,
                if: [{ name: "crp_v", value: [0,1]}],
            },
        ],
        resume: "{{ crp_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "ge_l",
                label: "La GE est ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'ge_v',
                type: "select",
                col: 3,
                show: true,
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negative" },
                    { id: 2, text: "Positive" },
                ],
                conditions: [
                    {
                        eval: "parseInt(i) === 1",
                        text: "La GE est Negative.",
                    },
                    {
                        eval: "parseInt(i) === 2",
                        text: "La GE est Positive avec une densité parasitaire a {{ gemg_v }} /µl.",
                    },
                ],
            },
            {
                name: "gea_l",
                label: "densité parasitaire ",
                type: "label",
                col: 3,
                show: true,
                if: [{ name: "ge_v", value: 2 }],
            },
            {
                name: 'gemg_v',
                type: "input",
                col: 2,
                show: true,
                if: [{ name: "ge_v", value: 2 }],
            },
            {
                name: "gemg_l",
                label: "/µl",
                type: "label",
                col: 2,
                show: true,
                if: [{ name: "ge_v", value: 2 }],
            },
            {
                name: "ge_space",
                type: "space",
                col: 7,
                show: true,
                if: [{ name: "ge_v", value: [0,1]}],
            },
        ],
        resume: "{{ ge_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "glyc_l",
                label: "La glycemie est ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'glyc_v',
                type: "input",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseFloat(v) >= 0.80 && parseFloat(v) <= 1",
                        text: "La glycemie est Normale a %v% mg/dl.",
                    },
                    {
                        eval: "parseFloat(v) < 0.80 ",
                        text: "La glycemie est Basse a %v% mg/dl.",
                    },
                    {
                        eval: "parseFloat(v) >= 1.10 && parseFloat(v) <= 1.3",
                        text: "La glycemie est Moderee Eleve a %v% mg/dl.",
                    },
                    {
                        eval: "parseFloat(v) > 1.30 && parseFloat(v) <= 2",
                        text: "La glycemie est Eleve a %v% mg/dl.",
                    },
                    {
                        eval: "parseFloat(v) > 2",
                        text: "La glycemie est Tres Eleve a %v% mg/dl.",
                    },
                ],
            },
            {
                name: "glycmg_l",
                label: "mg/dl",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: "al1h_space",
                type: "space",
                col: 5,
                show: true,
            },
        ],
        resume: "{{ glyc_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "uree_l",
                label: "L'urée est ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'uree_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Moderement eleve" },
                    { id: 3, text: "Eleve" },
                    { id: 4, text: "Tres Eleve" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "L'Urée est %v% a {{ ureemg_v }} mg/dl."
                    }
                ]
            },
            {
                name: "ureea_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'ureemg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "ureemg_l",
                label: "mg/dl",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ uree_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "creat_l",
                label: "La créatinine est ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'creat_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Moderement eleve" },
                    { id: 3, text: "Eleve" },
                    { id: 4, text: "Tres Eleve" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "La créatinine est %v% a {{ creatmg_v }} mg/dl."
                    }
                ]
            },
            {
                name: "creata_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'creatmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "creatmg_l",
                label: "mg/dl",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ creat_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "sgot_l",
                label: "SGOT ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'sgot_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Moderement eleve" },
                    { id: 3, text: "Eleve" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "La SGOT est %v% a {{ sgotmg_v }} Ul/l."
                    }
                ]
            },
            {
                name: "sgota_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'sgotmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "sgotmg_l",
                label: "Ul/l",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ sgot_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "sgpt_l",
                label: "SGPT ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'sgpt_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Moderement eleve" },
                    { id: 3, text: "Eleve" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "La SGPT est %v% a {{ sgptmg_v }} Ul/l."
                    }
                ]
            },
            {
                name: "sgpta_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'sgptmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "sgptmg_l",
                label: "Ul/l",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ sgpt_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [ // --------------------------------------------------------------------------------------------------------
            {
                name: "gamma_l",
                label: "Gamma GT ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'gamma_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Moderement eleve" },
                    { id: 3, text: "Eleve" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "La Gamma GT est %v% a {{ gammamg_v }} Ul/l."
                    }
                ]
            },
            {
                name: "gammaa_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'gammamg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "gammamg_l",
                label: "Ul/l",
                type: "label",
                col: 2,
                show: true,
            },],
        resume: "{{ gamma_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "tp_l",
                label: "TP ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'tp_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Amormal" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "Le TP est %v% a {{ tpmg_v }} %."
                    }
                ]
            },
            {
                name: "tpa_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'tpmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "tpmg_l",
                label: "%",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ tp_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "tck_l",
                label: "TCK ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'tck_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Amormal" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "Le TCK est %v% a {{ tckmg_v }} sec."
                    }
                ]
            },
            {
                name: "tcka_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'tckmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "tckmg_l",
                label: "sec",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ tck_v }}",
    },
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: "tsk_l",
                label: "TS ",
                type: "label",
                col: 2,
                show: true,
            },
            {
                name: 'tsk_v',
                type: "select",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Normale" },
                    { id: 2, text: "Amormal" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "Le TS est %v% a {{ tskmg_v }} sec."
                    }
                ]
            },
            {
                name: "tska_l",
                label: "a ",
                type: "label",
                col: 3,
                show: true,
            },
            {
                name: 'tskmg_v',
                type: "input",
                col: 2,
                show: true,
            },
            {
                name: "tskmg_l",
                label: "sec",
                type: "label",
                col: 2,
                show: true,
            },
        ],
        resume: "{{ tsk_v }}",
    },
    {
        groupName: "Paraclinique 2",
        template: [],
        resume: ""
    }
]