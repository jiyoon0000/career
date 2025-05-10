import React, { useEffect, useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  StyleSheet,
  Alert,
} from 'react-native';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { recommendSkills, saveSelectedSkills } from '@/api/Onboarding';

type SkillItem = {
  skillName: string;
};

export default function OnboardingSkillScreen() {
  const router = useRouter();
  const { jobName } = useLocalSearchParams<{ jobName: string }>();
  const [skills, setSkills] = useState<SkillItem[]>([]);
  const [selectedSkills, setSelectedSkills] = useState<string[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const result = await recommendSkills();
        setSkills(result);
      } catch (e) {
        Alert.alert('스킬 추천 실패', 'AI 추천 정보를 불러오지 못했어요.');
      }
    })();
  }, []);

  const toggleSkill = (skillName: string) => {
    setSelectedSkills(prev =>
      prev.includes(skillName)
        ? prev.filter(s => s !== skillName)
        : prev.length < 10
          ? [...prev, skillName]
          : prev
    );
  };

  const handleComplete = async () => {
    try {
      await saveSelectedSkills(selectedSkills);
      router.push('/(tabs)');
    } catch (e) {
      Alert.alert('스킬 저장 실패', '선택한 스킬 저장에 실패했어요.');
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scroll}>
        <View style={styles.header}>
          <View style={styles.progressBarBackground}>
            <View style={styles.progressBarFill} />
          </View>

          <Text style={styles.title}>추천 역량 중,{"\n"}목표하는 것을 선택해보세요!</Text>
          <View style={styles.jobWrapper}>
            <Text style={styles.asterisk}>*</Text>
            <Text style={styles.jobText}>{jobName}</Text>
          </View>
        </View>

        <View style={styles.tagContainer}>
          {skills.map(skill => (
            <TouchableOpacity
              key={skill.skillName}
              style={[
                styles.tag,
                selectedSkills.includes(skill.skillName) && styles.tagSelected,
              ]}
              onPress={() => toggleSkill(skill.skillName)}
            >
              <Text
                style={[
                  styles.tagText,
                  selectedSkills.includes(skill.skillName) && styles.tagTextSelected,
                ]}
              >
                {skill.skillName}
              </Text>
            </TouchableOpacity>
          ))}
        </View>

        <TouchableOpacity
          style={[
            styles.button,
            { backgroundColor: selectedSkills.length > 0 ? '#2379FA' : '#F7F7FB' },
          ]}
          onPress={handleComplete}
          disabled={selectedSkills.length === 0}
        >
          <Text
            style={[
              styles.buttonText,
              { color: selectedSkills.length > 0 ? '#FFFFFF' : '#999999' },
            ]}
          >
            선택 완료
          </Text>
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#FFFFFF' },
  scroll: { flex: 1 },
  header: { paddingHorizontal: 20, paddingTop: 44, paddingBottom: 12 },
  progressBarBackground: {
    height: 6,
    backgroundColor: '#F1F1F5',
    borderRadius: 100,
    marginBottom: 32,
  },
  progressBarFill: {
    width: 223,
    height: 6,
    backgroundColor: '#2379FA',
    borderRadius: 100,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111111',
    marginBottom: 8,
    width: 250,
  },
  jobWrapper: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  asterisk: {
    color: '#2379FA',
    fontSize: 14,
    marginRight: 4,
  },
  jobText: {
    color: '#767676',
    fontSize: 14,
  },
  tagContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    paddingHorizontal: 20,
    paddingTop: 20,
  },
  tag: {
    borderColor: '#F1F1F5',
    borderWidth: 1,
    borderRadius: 100,
    paddingVertical: 10,
    paddingHorizontal: 14,
    marginRight: 12,
    marginBottom: 12,
  },
  tagSelected: {
    borderColor: '#2379FA',
    backgroundColor: '#2379FA',
  },
  tagText: {
    color: '#999999',
    fontSize: 14,
  },
  tagTextSelected: {
    color: '#FFFFFF',
  },
  button: {
    borderRadius: 12,
    alignItems: 'center',
    paddingVertical: 14,
    marginTop: 32,
    marginHorizontal: 20,
    marginBottom: 40,
  },
  buttonText: {
    fontSize: 16,
    fontWeight: '700',
  },
});
