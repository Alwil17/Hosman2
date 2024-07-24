import {Section} from "../../field.model";

export const fields: Section[] = [
    {
        template: [
            {
                name: 'couleur_label',
                label: "Couleur",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'couleur',
                type: "select",
                options: [
                    { id: 1, text: "Rose" },
                    { id: 2, text: "Pale" },
                    { id: 3, text: "Cyanosé" },
                    { id: 4, text: "Icterique" },
                    { id: 5, text: "Erythrosique" },
                ],
                col: 4,
                show: true,
            },
        ],
        resume: "Il a une couleur {{ couleur }}."
    },
    {
        template: [
            {
                name: 'etat_label',
                label: "Etat general",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'etat',
                type: "select",
                options: [
                    { id: 1, text: "Conserve" },
                    { id: 2, text: "Passable" },
                    { id: 3, text: "Mauvais" },
                    { id: 4, text: "Mediocre" },
                    { id: 5, text: "Tres mauvais" },
                    { id: 6, text: "Altere" },
                    { id: 7, text: "Tres altere" },
                ],
                col: 4,
                show: true,
            },
        ],
        resume: "Son etat general est {{ etat }}."
    },
    {
        template: [
            {
                name: 'conscience_label',
                label: "Conscience",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'conscience',
                type: 'component',
                component: 'glasgow',
                col: 8,
                show: true
            },
        ],
        resume: "{{ conscience }}"
    },
    {
        template: [
            {
                name: 'fr_label',
                label: "Frequence respiratoire (/mn)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'fr',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "Frequence respiratoire: {{ fr }}."
    },
    {
        template: [
            {
                name: 'fc_label',
                label: "Frequence cardiaque (/mn)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'fc',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "Frequence cardiaque: {{ fc }}."
    },
    {
        template: [
            {
                name: 'so2_label',
                label: "Saturation en O2 (%)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'so2',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "Saturation en O2 (%): {{ so2 }}."
    },
    {
        template: [
            {
                name: 'temp_label',
                label: "Temperature (°C)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'temp',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "Temperature (°C): {{ temp }}."
    },
    {
        template: [
            {
                name: 'ta_label',
                label: "Tension Arterielle (mm/Hg)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'ta_mm',
                type: "input",
                col: 2,
                show: true,
                padding: false
            },
            {
                name: 'ta_hg',
                type: "input",
                col: 2,
                show: true,
                padding: false
            },
        ],
        resume: "Tension Arterielle (mm/Hg): {{ ta_mm }}/{{ ta_hg }}."
    },
    {
        template: [
            {
                name: 'poids_label',
                label: "Poids (Kg/g)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'poids_kg',
                type: "input",
                col: 2,
                show: true,
                padding: false
            },
            {
                name: 'poids_g',
                type: "input",
                col: 2,
                show: true,
                padding: false
            },
        ],
        resume: "Poids (Kg/g): {{ poids_kg }}/{{ poids_g }}."
    },
    {
        template: [
            {
                name: 'taille_label',
                label: "Taille (cm)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'taille',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "Taille (cm): {{ taille }}."
    },
    {
        template: [
            {
                name: 'pc_label',
                label: "PC (cm)",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'pc',
                type: "input",
                col: 4,
                show: true,
            },
        ],
        resume: "PC (cm): {{ pc }}."
    },
    {
        template: [
            {
                name: 's_label',
                label: "Score de silverman",
                type: 'label',
                col: 4,
                show:true,
            },
            {
                name: 'silverman',
                type: "component",
                component: 'silverman',
                col: 8,
                show: true,
            },
        ],
        resume: "{{ silverman }}."
    },

    {
        template:[
            {
                name: 'exam_comments',
                label: "Informations supplémentaires",
                type: 'textarea',
                col: 12,
                show:true,
            }
        ],
        resume: "{{ exam_comments }}"
    }
]