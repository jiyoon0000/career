import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  Image,
  Text,
  View,
  TouchableOpacity,
  Alert,
  StyleSheet,
} from 'react-native';
import { router } from 'expo-router';
import { completeOnboarding } from '@/api/Onboarding';

export default function OnboardingFinalScreen() {
  const handleComplete = async () => {
    try {
      await completeOnboarding();
      router.replace('/studyroom'); 
    } catch (e) {
      Alert.alert('오류', '온보딩 완료 처리에 실패했어요.');
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scroll}>
        <Image
          source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
          resizeMode="stretch"
          style={styles.headerImage}
        />
        <View style={styles.content}>
          <Text style={styles.text}>
            {"취뽀를 향한 첫걸음 완료!\n이제 한 걸음씩 함께 나아가볼까요?"}
          </Text>
          <TouchableOpacity style={styles.button} onPress={handleComplete}>
            <Text style={styles.buttonText}>체크인하기</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  scroll: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  headerImage: {
    height: 56,
    marginTop: 44,
  },
  content: {
    alignItems: 'center',
    paddingTop: 296,
  },
  text: {
    color: '#111111',
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 254,
    width: 234,
  },
  button: {
    alignItems: 'center',
    backgroundColor: '#2379FA',
    borderRadius: 12,
    paddingVertical: 14,
    marginTop: 12,
    marginBottom: 46,
    marginHorizontal: 20,
    alignSelf: 'stretch',
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
