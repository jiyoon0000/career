import { useEffect } from 'react';
import { router } from 'expo-router';
import {
    SafeAreaView,
    View,
    Text,
    ScrollView,
    Image,
  } from 'react-native';
import { checkOnboardingCompleted } from '@/api/Onboarding';

export default function LoadingScreen() {
  useEffect(() => {
    const timer = setTimeout(async () => {
      const completed = await checkOnboardingCompleted();
      if (completed) {
        router.replace('/onboarding/final');
      } else {
        router.back();
      }
    }, 1500);

    return () => clearTimeout(timer);
  }, []);

  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: '#FFFFFF' }}>
      <ScrollView style={{ flex: 1, backgroundColor: '#FFFFFF' }}>
        <Image
          source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
          style={{ height: 56, marginTop: 44 }}
          resizeMode="stretch"
        />
        <View style={{ alignItems: 'center', paddingTop: 220, paddingBottom: 350 }}>
          <Text
            style={{
              color: '#111111',
              fontSize: 18,
              fontWeight: 'bold',
              textAlign: 'center',
              width: 240,
            }}
          >
            {"취준 여정을 위한\n목표를 설정하는 중이에요...🎯\n잠시만 기다려 주세요!"}
          </Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}
