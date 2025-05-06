import React, { useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  StyleSheet,
  Alert,
} from 'react-native';
import { router } from 'expo-router';
import { sendVerificationCode } from '@/api/Auth';

export default function EmailInputScreen() {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSendCode = async () => {
    try {
      setLoading(true);
      console.log('📨 이메일 전송 시작', email);
  
      const res = await sendVerificationCode({ email });
      console.log('📩 응답 성공', res);
  
      Alert.alert('성공', res.message || '인증코드를 전송했어요!');
  
      console.log('➡️ 라우팅 이동 시작');
      router.push({
        pathname: '/(auth)/join/verify-code',
        params: { email },
      });
    } catch (error: any) {
      console.error('❌ 에러 발생', error);
      Alert.alert('오류', error.response?.data?.message || '인증코드 전송 실패');
    } finally {
      setLoading(false);
      console.log('🔄 로딩 종료');
    }
  };
  

  return (
    <SafeAreaView style={styles.container}>
      {/* 헤더 */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => router.back()}>
          <Image
            source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
            style={styles.backIcon}
          />
        </TouchableOpacity>
        <Text style={styles.title}>회원가입</Text>
      </View>

      {/* 진행도 */}
      <View style={styles.progressBar}>
        <View style={styles.progress} />
      </View>

      {/* 본문 */}
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
          disabled={!email || loading}
        >
          <Text style={[styles.buttonText, { color: email ? '#fff' : '#999' }]}>
            {loading ? '전송 중...' : '인증코드 발송'}
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
