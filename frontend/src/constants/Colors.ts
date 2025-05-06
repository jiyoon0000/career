/**
 * Below are the colors that are used in the app. The colors are defined in the light and dark mode.
 * There are many other ways to style your app. For example, [Nativewind](https://www.nativewind.dev/), [Tamagui](https://tamagui.dev/), [unistyles](https://reactnativeunistyles.vercel.app), etc.
 */

const tintColorLight = '#0a7ea4';
const tintColorDark = '#fff';

export const Colors = {
  light: {
    text: '#11181C',
    background: '#fff',
    tint: tintColorLight,
    icon: '#687076',
    tabIconDefault: '#687076',
    tabIconSelected: tintColorLight,
  },
  dark: {
    text: '#ECEDEE',
    background: '#151718',
    tint: tintColorDark,
    icon: '#9BA1A6',
    tabIconDefault: '#9BA1A6',
    tabIconSelected: tintColorDark,
  },

  PRIMARY: '#345EED',
  SECONDARY: '#F2F5FC',
  BACKGROUND: '#FFFFFF',
  GRAY100: '#F5F5F5',
  GRAY200: '#E0E0E0',
  GRAY300: '#BDBDBD',
  GRAY400: '#9E9E9E',
  GRAY500: '#757575',
  TEXT_BLACK: '#333333',
  TEXT_GRAY: '#828282',
  ERROR: '#FF4D4F',
};
