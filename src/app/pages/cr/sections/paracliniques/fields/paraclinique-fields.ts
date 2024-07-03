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
        resume: ""
    },
    {
        groupName: "Paraclinique 2",
        template: [

        ],
        resume: 'wwww 555 '
    }
]