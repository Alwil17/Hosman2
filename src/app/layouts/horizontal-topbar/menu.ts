import { MenuItem } from "./menu.model";

export const MENU: MenuItem[] = [
  {
    id: 1,
    label: "MENUITEMS.MENU.SECRETARIAT.TEXT",
    isTitle: true,
  },
  {
    id: 2,
    label: "MENUITEMS.PATIENTS.LIST.PATIENT_NEW",
    link: "/secretariat/patients/patient-new",
    icon: "ri-user-add-fill",
  },
  {
    id: 3,
    label: "MENUITEMS.PATIENTS.LIST.PATIENTS_LIST",
    link: "/secretariat/patients/patient-list",
    icon: "ri-user-search-fill",
  },
  {
    id: 4,
    label: "MENUITEMS.PATIENTS.LIST.DEBTS",
    link: "/secretariat/patients/patient-debts",
    icon: "ri-funds-fill",
  },
  {
    id: 5,
    label: "MENUITEMS.PATIENTS.LIST.RESTS",
    link: "/secretariat/patients/patient-rests",
    icon: "ri-refund-2-fill",
  },
  {
    id: 6,
    label: "MENUITEMS.ACTIVITIES.LIST.EXPENSES",
    link: "/secretariat/activities/expenses",
    icon: "ri-indeterminate-circle-fill",
  },
  {
    id: 7,
    label: "MENUITEMS.ACTIVITIES.LIST.COLLECTIONS",
    link: "/secretariat/activities/collections",
    icon: "ri-add-circle-fill",
  },
  {
    id: 8,
    label: "MENUITEMS.ACTIVITIES.TEXT",
    link: "/secretariat/activities/all",
    icon: "mdi mdi-sticker-text-outline",
  },
  {
    id: 12,
    label: "MENUITEMS.INFORMATIONS.TEXT",
    link: "/secretariat/informations",
    icon: "ri-information-line",
    subItems: [
      {
        id: 13,
        label: "MENUITEMS.INFORMATIONS.LIST.TARIFFS",
        link: "/tariffs",
        parentId: 12,
      },
      {
        id: 14,
        label: "MENUITEMS.INFORMATIONS.LIST.CIM_11",
        link: "/cim_11",
        parentId: 12,
      },
      {
        id: 15,
        label: "MENUITEMS.INFORMATIONS.LIST.TELEPHONE_BOOK",
        link: "/telephone_book",
        parentId: 12,
      },
      {
        id: 16,
        label: "MENUITEMS.INFORMATIONS.LIST.HOSPITALIZED_PATIENTS",
        link: "/hospitalized_patients",
        parentId: 12,
      },
    ],
  },
  // {
  //   id: 1,
  //   label: "MENUITEMS.MENU.SECRETARIAT.TEXT",
  //   isTitle: true,
  // },
  // {
  //   id: 2,
  //   label: "MENUITEMS.PATIENTS.TEXT",
  //   link: "/secretariat/patients",
  //   icon: "ri-user-5-line",
  //   subItems: [
  //     {
  //       id: 3,
  //       label: "MENUITEMS.PATIENTS.LIST.PATIENT_NEW",
  //       link: "/patient-new",
  //       parentId: 2,
  //     },
  //     {
  //       id: 4,
  //       label: "MENUITEMS.PATIENTS.LIST.PATIENTS_LIST",
  //       link: "/patient-list",
  //       parentId: 2,
  //     },
  //     {
  //       id: 5,
  //       label: "MENUITEMS.PATIENTS.LIST.DEBTS",
  //       link: "/patient-debts",
  //       parentId: 2,
  //     },
  //     {
  //       id: 6,
  //       label: "MENUITEMS.PATIENTS.LIST.RESTS",
  //       link: "/patient-rests",
  //       parentId: 2,
  //     },
  //   ],
  // },
  // {
  //   id: 7,
  //   label: "MENUITEMS.ACTIVITIES.TEXT",
  //   link: "/secretariat/activities",
  //   icon: "mdi mdi-sticker-text-outline",
  //   subItems: [
  //     {
  //       id: 8,
  //       label: "MENUITEMS.ACTIVITIES.LIST.EXPENSES",
  //       link: "/expenses",
  //       parentId: 7,
  //     },
  //     {
  //       id: 9,
  //       label: "MENUITEMS.ACTIVITIES.LIST.COLLECTIONS",
  //       link: "/collections",
  //       parentId: 7,
  //     },
  //     {
  //       id: 10,
  //       label: "MENUITEMS.ACTIVITIES.LIST.RECEIPTS",
  //       link: "/receipts",
  //       parentId: 7,
  //     },
  //     {
  //       id: 11,
  //       label: "MENUITEMS.ACTIVITIES.LIST.ACCOUNT_SHEET",
  //       link: "/account_sheet",
  //       parentId: 7,
  //     },
  //   ],
  // },
  // {
  //   id: 12,
  //   label: "MENUITEMS.INFORMATIONS.TEXT",
  //   link: "/secretariat/informations",
  //   icon: "ri-information-line",
  //   subItems: [
  //     {
  //       id: 13,
  //       label: "MENUITEMS.INFORMATIONS.LIST.TARIFFS",
  //       link: "/tariffs",
  //       parentId: 12,
  //     },
  //     {
  //       id: 14,
  //       label: "MENUITEMS.INFORMATIONS.LIST.CIM_11",
  //       link: "/cim_11",
  //       parentId: 12,
  //     },
  //     {
  //       id: 15,
  //       label: "MENUITEMS.INFORMATIONS.LIST.TELEPHONE_BOOK",
  //       link: "/telephone_book",
  //       parentId: 12,
  //     },
  //     {
  //       id: 16,
  //       label: "MENUITEMS.INFORMATIONS.LIST.HOSPITALIZED_PATIENTS",
  //       link: "/hospitalized_patients",
  //       parentId: 12,
  //     },
  //   ],
  // },
];
