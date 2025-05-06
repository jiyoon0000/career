import React, { useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  StyleSheet,
} from 'react-native';
import { router } from 'expo-router';

export default function EmailInputScreen() {
  const [email, setEmail] = useState('');

  const handleSendCode = () => {
    // 이후 서버 통신 추가 가능
    router.push('/(auth)/join/Verify-code');
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => router.back()}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.backIcon}
          />
        </TouchableOpacity>
        <Text style={styles.title}>회원가입</Text>
      </View>

      <View style={styles.progressBar}>
        <View style={styles.progress} />
      </View>

      <View style={styles.content}>
        <Text style={styles.heading}>로그인에 사용할{'\n'}이메일을 알려주세요</Text>
        <Text style={styles.sub}>아무에게도 공개되지 않으니 걱정마세요!</Text>

        <View style={styles.inputWrapper}>
          <TextInput
            placeholder="이메일을 입력해 주세요"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            style={styles.input}
          />
        </View>

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: email ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleSendCode}
          disabled={!email}
        >
          <Text style={[styles.buttonText, { color: email ? '#fff' : '#999' }]}>
            인증코드 발송
          </Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#fff' },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: 44,
    paddingHorizontal: 20,
  },
  backIcon: { width: 36, height: 44 },
  title: { fontSize: 20, fontWeight: 'bold', color: '#111' },
  progressBar: {
    height: 6,
    backgroundColor: '#F1F1F5',
    borderRadius: 100,
    marginHorizontal: 20,
    marginTop: 8,
  },
  progress: {
    width: '33%',
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
  },
  content: {
    paddingHorizontal: 20,
    paddingTop: 32,
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111',
    marginBottom: 8,
  },
  sub: { fontSize: 14, color: '#767676', marginBottom: 32 },
  inputWrapper: {
    borderWidth: 1,
    borderColor: '#E5E5EC',
    borderRadius: 12,
    paddingHorizontal: 16,
    paddingVertical: 4,
    marginBottom: 24,
  },
  input: {
    fontSize: 16,
    color: '#111',
    paddingVertical: 10,
  },
  button: {
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
  },
  buttonText: { fontSize: 16, fontWeight: 'bold' },
});
