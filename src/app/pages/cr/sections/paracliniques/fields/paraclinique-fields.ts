import {Section} from "../../../field.model";

export const paracliniqueFields: Section[] = [
    // Globule Blancs
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
    // Hémoglobine
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
    // VS
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
                name: 'vs_v',
                type: "input",
                label: "VS :",
                units: "mm",
                col: 6,
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
            // {
            //     name: "al1mm_l",
            //     label: "mm",
            //     type: "label",
            //     col: 1,
            //     show: true,
            // },
            // {
            //     name: "al1h_space",
            //     type: "space",
            //     col: 8,
            //     show: true,
            // },
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
    // Groupe sanguin
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'grsang',
                label: "Groupe Sanguin",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "A+" },
                    { id: 2, text: "B+" },
                    { id: 3, text: "AB+" },
                    { id: 4, text: "O+" },
                    { id: 5, text: "A-" },
                    { id: 6, text: "B-" },
                    { id: 7, text: "AB-" },
                    { id: 8, text: "O-" },
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "Le groupe sanguin est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ grsang }}"
    },
    // Rhesus
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'rhsang',
                label: "Rhesus",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" }
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "Le Rhesus est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ rhsang }}"
    },
    // CRP
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'crp_v',
                type: "select",
                label: "La CRP est ",
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
                name: 'crpmg_v',
                type: "input",
                label: "a ",
                units: "mg/l",
                col: 3,
                show: true,
                // if: [{ name: "crp_v", value: 2 }],
            },
        ],
        resume: "{{ crp_v }}",
    },
    // SODIUM
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "sodiummmol",
                type: "input",
                label: "Sodium",
                units: "132-150 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Sodium est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ sodiummmol }}"
    },
    // POTASSIUM
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "potassiummol",
                type: "input",
                label: "Potassium",
                units: "3.5-5 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Potassium est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ potassiummol }}"
    },
    // GE
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'ge_v',
                type: "select",
                label: "La GE est ",
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
                name: 'gemg_v',
                type: "input",
                label: "densité parasitaire ",
                units: "/µl",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ ge_v }}",
    },
    // Chlore
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "chloremmol",
                type: "input",
                label: "Chlore",
                units: "98-110 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Chlore est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ chloremmol }}"
    },
    // calcium
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "calciummmol",
                type: "input",
                label: "Calcium",
                units: "2.2-2.6 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Calcium est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ calciummmol }}"
    },
    // glycemie
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'glyc_v',
                type: "input",
                label: "La glycemie est ",
                units: "mg/dl",
                col: 6,
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
        ],
        resume: "{{ glyc_v }}",
    },
    // Phosphore
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "phosphoremmol",
                type: "input",
                label: "Phosphore",
                units: "0.7-1.3 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Phosphore est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ phosphoremmol }}"
    },
    // Magnesium
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "magnesiummmol",
                type: "input",
                label: "Magnesium",
                units: "0.65-1.05 mmol/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "v !== null",
                        text: "Le Magnesium est %v% mmol/l.",
                    }
                ],
            },
        ],
        resume: "{{ magnesiummmol }}"
    },
    // urée
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'uree_v',
                type: "select",
                label: "L'urée est ",
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
                name: 'ureemg_v',
                type: "input",
                label: "a ",
                units: "mg/dl",
                col: 3,
                show: true,
            }
        ],
        resume: "{{ uree_v }}",
    },
    // Glucose
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'glucosesang',
                label: "Glucose",
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
                        eval: "parseInt(i) !== 0",
                        text: "Le glucose est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ glucosesang }}"
    },
    // Albumine
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'albumine_v',
                label: "Albumine",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" }
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "L'Albumine sanguin est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ albumine_v }}"
    },
    // créatinine
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'creat_v',
                type: "select",
                label: "La créatinine est ",
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
                name: 'creatmg_v',
                type: "input",
                label: "a ",
                units: "mg/dl",
                col: 3,
                show: true,
            }
        ],
        resume: "{{ creat_v }}",
    },
    // Sang
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'sangv',
                label: "Sang",
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
                        eval: "parseInt(i) !== 0",
                        text: "Le sang est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ sangv }}"
    },
    // Urobilinogne
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'urobilinogne_v',
                label: "Urobilinogene",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" }
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "L'Urobilinogene sanguin est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ urobilinogne_v }}"
    },
    // SGOT
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'sgot_v',
                type: "select",
                label: "SGOT ",
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
                name: 'sgotmg_v',
                type: "input",
                label: "a ",
                units: "Ul/l",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ sgot_v }}",
    },
    // Cetones
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'cetonesv',
                label: "Cetones",
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
                        eval: "parseInt(i) !== 0",
                        text: "Les Cetones est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ cetonesv }}"
    },
    // Leucocytes
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'leucocytes_v',
                label: "Leucocytes",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" }
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "Les Leucocytes sont %v%.",
                    },
                ],
            },
        ],
        resume : "{{ leucocytes_v }}"
    },
    // SGPT
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'sgpt_v',
                type: "select",
                label: "SGPT ",
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
                name: 'sgptmg_v',
                type: "input",
                label: "a ",
                units: "Ul/l",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ sgpt_v }}",
    },
    // Amylasurie
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'amylasurie_v',
                label: "Amylasurie",
                type: "input",
                units:"<600 Ul/l",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "L'Amylasurie est a %v% Ul/l.",
                    },
                ],
            },
        ],
        resume : "{{ amylasurie_v }}"
    },
    // pH
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'ph_v',
                label: "pH",
                type: "input",
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "Le pH est a %v%.",
                    },
                ],
            },
        ],
        resume : "{{ ph_v }}"
    },
    // Gamma GT
    {
        groupName: "Paraclinique 1",
        template: [ // --------------------------------------------------------------------------------------------------------
            {
                name: 'gamma_v',
                type: "select",
                label: "Gamma GT ",
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
                name: 'gammamg_v',
                type: "input",
                label: "a ",
                units: "Ul/l",
                col: 3,
                show: true,
            },
          ],
        resume: "{{ gamma_v }}",
    },
    // Netrites
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'etritesv',
                label: "Netrites",
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
                        eval: "parseInt(i) !== 0",
                        text: "Les Netrites sont %v%.",
                    },
                ],
            },
        ],
        resume : "{{ etritesv }}"
    },
    // Bilirubine
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'bilirubine_v',
                label: "Bilirubine",
                type: "select",
                default: 0,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" }
                ],
                col: 3,
                show: true,
                conditions: [
                    {
                        eval: "parseInt(i) !== 0",
                        text: "La Bilirubine est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ bilirubine_v }}"
    },
    // TP
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'tp_v',
                type: "select",
                label: "TP ",
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
                name: 'tpmg_v',
                type: "input",
                label: "a ",
                units: "%",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ tp_v }}",
    },
    // Ac. ascorbinque
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'ascorbinquev',
                label: "Ac. ascorbinque",
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
                        eval: "parseInt(i) !== 0",
                        text: "L'Ac. ascorbinque est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ ascorbinquev }}"
    },
    // Immunoglobuline E
    {
        groupName: "Paraclinique 1",
        template: [
            {
                name: 'immunoglobulinev',
                label: "Immunoglobuline E",
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
                        eval: "parseInt(i) !== 0",
                        text: "L'Immunoglobuline E est %v%.",
                    },
                ],
            },
        ],
        resume : "{{ immunoglobulinev }}"
    },
    // TCK
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'tck_v',
                type: "select",
                label: "TCK ",
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
                name: 'tckmg_v',
                type: "input",
                label: "a ",
                units: "sec",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ tck_v }}",
    },
    //**************** SPACE 6 ***********************
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "al1h_space",
                type: "space",
                col: 6,
                show: true,
            },
        ],
        resume: ""
    },
    // TS
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'tsk_v',
                type: "select",
                label: "TS ",
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
                name: 'tskmg_v',
                type: "input",
                label: "a ",
                units: "sec",
                col: 3,
                show: true,
            },
        ],
        resume: "{{ tsk_v }}",
    },
    //**************** SPACE 6 ***********************
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "al1h_space",
                type: "space",
                col: 6,
                show: true,
            },
        ],
        resume: ""
    },
    // TDR du paludisme
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'tdrp_v',
                type: "select",
                label: "TDR du paludisme ",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "Le TDR du paludisme est %v%."
                    }
                ]
            },
        ],
        resume: "{{ tdrp_v }}",
    },
    // TDR de la dengue
    {
        groupName: "Paraclinique 1",
        template: [
            // --------------------------------------------------------------------------------------------------------
            {
                name: 'tdrd_v',
                type: "select",
                label: "TDR de la dengue ",
                col: 3,
                show: true,
                options: [
                    { id: 0, text: "Selectionner une valeur" },
                    { id: 1, text: "Negatif" },
                    { id: 2, text: "Positif" },
                ],
                conditions : [
                    {
                        eval : "v !== null && v !== 0",
                        text: "Le TDR de la dengue est %v%."
                    }
                ]
            },
        ],
        resume: "{{ tdrd_v }}",
    },
    //**************** SPACE 6 ***********************
    {
        groupName: "Paraclinique 1",
        template:[
            {
                name: "al1h_space",
                type: "space",
                col: 6,
                show: true,
            },
        ],
        resume: ""
    },




    {
        groupName: "Paraclinique 2",
        template: [],
        resume: ""
    }
]