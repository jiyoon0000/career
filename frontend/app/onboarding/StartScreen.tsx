import React from 'react';
import { View, Image, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { useRouter } from 'expo-router';

export default function StartScreen() {
  const router = useRouter();

  const handlePress = () => {
    router.push('/onboarding/search-job');
  };

  return (
    <View style={styles.container}>

      <View style={styles.badge}>
        <Text style={styles.badgeText}>체크인 완료!</Text>
      </View>

      <Image
        source={require('@/assets/images/tossface-party-100.png')}
        style={styles.emoji}
        resizeMode="contain"
      />

      <View style={styles.textWrapper}>
        <Text style={styles.textLine}>
          <Text style={styles.accent}>커리어룸</Text>
          <Text style={styles.default}>과 함께</Text>
        </Text>
        <Text style={styles.textLine}>
          <Text style={styles.default}>매일 꾸준히 </Text>
          <Text style={styles.accent}>체크인</Text>
          <Text style={styles.default}>하고</Text>
        </Text>
        <Text style={styles.textLine}>
          <Text style={styles.accent}>목표</Text>
          <Text style={styles.default}>를 이뤄보세요!</Text>
        </Text>
      </View>

      <TouchableOpacity style={styles.button} onPress={handlePress}>
        <Text style={styles.buttonText}>목표 설정하기</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 100,
  },
  logo: {
    width: 150,
    height: 60,
    marginBottom: 40,
  },
  badge: {
    backgroundColor: '#F7F7FB',
    borderRadius: 100,
    paddingVertical: 8,
    paddingHorizontal: 12,
    marginBottom: 24,
  },
  badgeText: {
    color: '#111111',
    fontSize: 14,
  },
  emoji: {
    width: 100,
    height: 100,
    marginBottom: 24,
  },
  textWrapper: {
    alignItems: 'center',
    marginBottom: 100,
  },
  textLine: {
    flexDirection: 'row',
    fontSize: 24,
    fontWeight: '700',
  },
  accent: {
    color: '#2379FA',
    fontSize: 24,
    fontWeight: '700',
  },
  default: {
    color: '#111111',
    fontSize: 24,
    fontWeight: '700',
  },
  button: {
    alignSelf: 'stretch',
    backgroundColor: '#2379FA',
    paddingVertical: 14,
    borderRadius: 12,
    alignItems: 'center',
    marginBottom: 46,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '700',
  },
});
