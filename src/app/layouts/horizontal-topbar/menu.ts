import { link } from "fs";
import { MenuItem } from "./menu.model";

export const SECRETARIAT_MENU: MenuItem[] = [
  {
    id: 1,
    label: "MENUITEMS.MENU.SECRETARIAT.TEXT",
    isTitle: true,
    bgColor: "#32CD32",
  },
  {
    id: 2,
    label: "MENUITEMS.PATIENTS.LIST.PATIENT_NEW",
    link: "/secretariat/patients/patient-create",
    // icon: "ri-user-add-fill",
    bgColor: "#98FB98",
  },
  {
    id: 3,
    label: "MENUITEMS.PATIENTS.LIST.PATIENTS_LIST",
    link: "/secretariat/patients/patient-list",
    // icon: "ri-user-search-fill",
    textColor: "#FFFFFF",
    bgColor: "#90EE90",
  },
  {
    id: 4,
    label: "MENUITEMS.INFORMATIONS.LIST.TELEPHONE_BOOK",
    link: "/secretariat/informations/phone_book",
    // icon: "ri-contacts-book-2-fill",
    textColor: "#FFFFFF",
    bgColor: "#40B5AD",
  },
  {
    id: 5,
    label: "MENUITEMS.EXTERN.LIST.APPOINTMENT",
    link: "/secretariat/appointment",
    // icon: "ri-calendar-event-fill",
    // icon: "mdi mdi-calendar-cursor",
    textColor: "#FFFFFF",
    bgColor: "#008080",
  },
  {
    id: 6,
    label: "MENUITEMS.EXTERN.LIST.MESSAGING",
    link: "/extern/messaging",
    // icon: "ri-message-3-fill",
    textColor: "#FFFFFF",
    bgColor: "#36454F",
  },
  {
    id: 7,
    label: "MENUITEMS.ACTIVITIES.LIST.EXPENSES",
    link: "/secretariat/expenses",
    // icon: "ri-indeterminate-circle-fill",
    textColor: "#FFFFFF",
    bgColor: "#000000",
  },
  {
    id: 8,
    label: "MENUITEMS.PATIENTS.LIST.DEBTS",
    link: "/secretariat/patients/patient-debts",
    // icon: "ri-funds-fill",
    bgColor: "#E5E4E2",
  },
  {
    id: 9,
    label: "MENUITEMS.EXTERN.LIST.HOSPITALIZED_PATIENTS",
    link: "/extern/hospitalized_patients",
    // icon: "ri-hotel-bed-fill",
    bgColor: "#FFFFFF",
  },
  {
    id: 10,
    label: "MENUITEMS.INFORMATIONS.LIST.TARIFFS",
    link: "/secretariat/tariffs",
    // icon: "ri-file-list-3-fill",
    textColor: "#FFFFFF",
    bgColor: "#6E260E",
  },
  {
    id: 11,
    label: "MENUITEMS.ACTIVITIES.LIST.COLLECTIONS",
    link: "/secretariat/collections",
    // icon: "ri-add-circle-fill",
    bgColor: "#98FB98",
  },
  {
    id: 12,
    label: "MENUITEMS.ACTIVITIES.TEXT",
    link: "/secretariat/activities/all",
    // icon: "mdi mdi-sticker-text-outline",
  },
  {
    id: 13,
    label: "MENUITEMS.INFORMATIONS.LIST.EXAMINATIONS",
    link: "/extern/exams",
    // icon: "ri-file-text-fill",
  },
  {
    id: 14,
    label: "MENUITEMS.OTHERS.TEXT",
    link: "",
    // icon: "ri-information-line",
    subItems: [
      {
        id: 15,
        label: "MENUITEMS.PATIENTS.LIST.WAITING_LIST",
        link: "/secretariat/patients/patient-waiting-list",
        parentId: 14,
      },
      {
        id: 16,
        label: "MENUITEMS.PATIENTS.LIST.RESTS",
        link: "/secretariat/patients/patient-rests",
        parentId: 14,
      },
      {
        id: 17,
        label: "MENUITEMS.INFORMATIONS.LIST.CIM_11",
        link: "/secretariat/informations/cim_11",
        parentId: 14,
      },
    ],
  },
];

export const MEDICAL_BASE_MENU: MenuItem[] = [
  {
    id: 1,
    label: "Base médicale",
    isTitle: true,
  },
  {
    id: 2,
    label: "Liste d'attente",
    link: "/medical-base/patient-waiting-list",
    // icon: "ri-file-list-3-fill",
    textColor: "#FFFFFF",
    bgColor: "#B2BEB5",
  },
  {
    id: 3,
    label: "Consulter",
    link: "",
    textColor: "#FFFFFF",
    bgColor: "#008080",
    subItems: [
      {
        id: 4,
        label: "Anciens Patients",
        link: "/medical-base/patient-list",
        parentId: 3,
      },
      {
        id: 5,
        label: "Nouveau Patients",
        link: "/medical-base/patient-list",
        parentId: 3,
      },
      {
        id: 6,
        label: "Patients récents",
        parentId: 3,
        subItems: [
          {
            id: 7,
            parentId: 6,
            link: "/medical-base/patient-recent/today",
            label: "d'Aujourd'hui",
          },
          {
            id: 8,
            parentId: 6,
            link: "/medical-base/patient-recent/yesterday",
            label: "d'Hier"
          },
          {
            id: 9,
            parentId: 6,
            link: "/medical-base/patient-recent/week",
            label: "de la Semaine"
          }
        ]
      }
    ]
  },
  {
    id: 10,
    label: "Requêtes spéciales",
    link: "",
    subItems: [
      {
        id: 11,
        label: "Consultations antérieures",
        parentId: 10,
        subItems: [
          {
            id: 12,
            parentId: 11,
            link: "/medical-base/patient-recent/today",
            label: "d'Aujourd'hui",
          },
          {
            id: 13,
            parentId: 11,
            link: "/medical-base/patient-recent/yesterday",
            label: "d'Hier"
          },
          {
            id: 14,
            parentId: 11,
            link: "/medical-base/patient-recent/week",
            label: "de la Semaine"
          }
        ]
      },
      {
        id: 15,
        label: "Générale",
        parentId: 10,
        link: "/medical-base/special-requests/general"
      },
      {
        id: 16,
        label: "Par Secteur",
        parentId: 10,
        link: "/medical-base/special-requests/sector"
      },
      {
        id: 17,
        label: "Par docteur",
        parentId: 10,
        link: "/medical-base/special-requests/doctor"
      },
      {
        id: 18,
        label: "Prises en charge par médecin",
        parentId: 10,
        link: "/medical-base/requetes-pec"
      }
    ],
  },
  {
    id: 5,
    label: "REQUETES",
    link: "/medical-base/requetes-jaunes",
    textColor: "#000000",
    bgColor: "#FFFF00",
  },
  {
    id: 6,
    label: "MEDOR",
    link: "/medical-base/medicines-prescriptions",
    // icon: "ri-user-search-fill",
  },
];
