import React, { useEffect, useState } from 'react';
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
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function VerificationCodeScreen() {
  const [code, setCode] = useState('');
  const [email, setEmail] = useState('');

  useEffect(() => {
    const fetchEmail = async () => {
      const storedEmail = await AsyncStorage.getItem('signup_email');
      if (storedEmail) setEmail(storedEmail);
    };
    fetchEmail();
  }, []);

  const handleVerify = () => {
    // TODO: 인증코드 검증 로직 추가 예정
    router.push('/(auth)/join/Password');
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
        <Text style={styles.heading}>
          이메일로 발송된{'\n'}인증코드를 입력해 주세요
        </Text>
        <Text style={styles.sub}>{email} 으로 보냈어요!</Text>

        <View style={styles.inputWrapper}>
          <TextInput
            placeholder="인증코드를 입력해 주세요"
            value={code}
            onChangeText={setCode}
            keyboardType="number-pad"
            style={styles.input}
          />
        </View>

        <View style={styles.resendRow}>
          <Text style={styles.resendText}>인증코드 메일이 오지 않았나요?</Text>
          <Text style={styles.resendButton}>재전송</Text>
        </View>

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: code ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleVerify}
          disabled={!code}
        >
          <Text style={[styles.buttonText, { color: code ? '#fff' : '#999' }]}>
            인증코드 확인
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
    width: '66%',
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
  resendRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 12,
  },
  resendText: { color: '#767676', fontSize: 14, flex: 1 },
  resendButton: { color: '#2379FA', fontSize: 14 },
  button: {
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
  },
  buttonText: { fontSize: 16, fontWeight: 'bold' },
});
