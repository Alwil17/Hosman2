import { MenuItem } from "./menu.model";

export const SECRETARIAT_MENU: MenuItem[] = [
  {
    id: 1,
    label: "MENUITEMS.MENU.SECRETARIAT.TEXT",
    isTitle: true,
  },
  {
    id: 2,
    label: "MENUITEMS.PATIENTS.LIST.PATIENT_NEW",
    link: "/secretariat/patients/patient-create",
    // icon: "ri-user-add-fill",
  },
  {
    id: 3,
    label: "MENUITEMS.PATIENTS.LIST.PATIENTS_LIST",
    link: "/secretariat/patients/patient-list",
    // icon: "ri-user-search-fill",
  },
  {
    id: 4,
    label: "MENUITEMS.INFORMATIONS.LIST.TELEPHONE_BOOK",
    link: "/secretariat/informations/phone_book",
    // icon: "ri-contacts-book-2-fill",
  },
  {
    id: 5,
    label: "MENUITEMS.EXTERN.LIST.APPOINTMENT",
    link: "/secretariat/appointment",
    // icon: "ri-calendar-event-fill",
    // icon: "mdi mdi-calendar-cursor",
  },
  {
    id: 6,
    label: "MENUITEMS.EXTERN.LIST.MESSAGING",
    link: "/extern/messaging",
    // icon: "ri-message-3-fill",
  },
  {
    id: 7,
    label: "MENUITEMS.ACTIVITIES.LIST.EXPENSES",
    link: "/secretariat/expenses",
    // icon: "ri-indeterminate-circle-fill",
  },
  {
    id: 8,
    label: "MENUITEMS.PATIENTS.LIST.DEBTS",
    link: "/secretariat/patients/patient-debts",
    // icon: "ri-funds-fill",
  },
  {
    id: 9,
    label: "MENUITEMS.EXTERN.LIST.HOSPITALIZED_PATIENTS",
    link: "/extern/hospitalized_patients",
    // icon: "ri-hotel-bed-fill",
  },
  {
    id: 10,
    label: "MENUITEMS.INFORMATIONS.LIST.TARIFFS",
    link: "/secretariat/tariffs",
    // icon: "ri-file-list-3-fill",
  },
  {
    id: 11,
    label: "MENUITEMS.ACTIVITIES.LIST.COLLECTIONS",
    link: "/secretariat/collections",
    // icon: "ri-add-circle-fill",
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
  },
  {
    id: 3,
    label: "Consulter",
    link: "/medical-base/patient-list",
    // icon: "ri-user-search-fill",
  },
  {
    id: 4,
    label: "MEDOR",
    link: "/medical-base/medicines-prescriptions",
    // icon: "ri-user-search-fill",
  },
];
