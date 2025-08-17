# Features

A user can give AI access to all provided data so it can ask relevant questions and give better suggestions.

## 1. Mental module

### 1.1. Mood tracking

**Description:**  
A user can quickly select their current mood from the [Circumplex model](https://en.wikipedia.org/wiki/Emotion_classification#Circumplex_model).  
Both the user and AI can explore mood history later.

Possible values are:
- 😐 Neutral
- 🧐 Alert
- 🤩 Excited
- 😄 Happy
- 😊 Content
- 😌 Relaxed
- 🧘 Calm
- 😑 Bored
- 😞 Depressed
- 😢 Sad
- 😰 Distressed
- 😠 Angry
- 😬 Tense

**User Stories:**
- As a user, I want to select my current mood so I can track my feelings.
- As a user, I want to see my mood history so I can notice patterns.

**Acceptance Criteria:**
- Mood can be set multiple times per day.
- Mood history can be viewed (e.g., as a calendar or chart).
- Neutral mood is shown at the center of the UI.

**UI Mockup:**
Mood selection is visualized as a circumplex, with 😐 Neutral at the center and other moods arranged around it:

```
    😬  🧐
  😠      🤩
😰          😄
      😐
😢          😊
  😞      😌
    😑  🧘
```