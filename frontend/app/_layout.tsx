import { DarkTheme, DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { useFonts } from 'expo-font';
import { Slot, Stack } from 'expo-router';
import * as SplashScreen from 'expo-splash-screen';
import { StatusBar } from 'expo-status-bar';
import { useEffect } from 'react';
import 'react-native-reanimated';

import { useColorScheme } from '@/hooks/useColorScheme';

SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  const colorScheme = useColorScheme();
  const [loaded] = useFonts({
    SpaceMono: require('../src/assets/fonts/SpaceMono-Regular.ttf'),
    PretendardRegular: require('@/assets/fonts/Pretendard-Regular.ttf'),
    PretendardSemiBold: require('@/assets/fonts/Pretendard-SemiBold.ttf'),
  });

  useEffect(() => {
    if (loaded) {
      SplashScreen.hideAsync();
    }
  }, [loaded]);

  if (!loaded) {
    return null;
  }

  return (
    <ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
      <Stack>
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />

        <Stack.Screen name="onboarding/loading" options={{ headerShown: false }} />
        <Stack.Screen name="onboarding/final" options={{ headerShown: false }} />
        <Stack.Screen name="onboarding/search-job" options={{ headerShown: false }} />
        <Stack.Screen name="onboarding/skill" options={{ headerShown: false }} />
        <Stack.Screen name="onboarding/certificate" options={{ headerShown: false }} />

        <Stack.Screen name="(auth)/join/email" options={{ headerShown: false }} />
        <Stack.Screen name="(auth)/join/verify-code" options={{ headerShown: false }} />
        <Stack.Screen name="(auth)/join/password" options={{ headerShown: false }} />

        <Stack.Screen name="+not-found" />
      </Stack>

      <StatusBar style="auto" />
    </ThemeProvider>
  );
}
