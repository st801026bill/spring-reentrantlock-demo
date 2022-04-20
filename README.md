**生產者消費者模式實作**
---
## 一. 基本介紹
ReentrantLock. 可重入鎖，是屬於Lock的一種實作，功能與synchronized相似，  
差別在於ReentrantLock需要由開發人員控制```lock```與```unlock```，  
synchronized則是會自動控制鎖。

## 二. 類別介紹
**1. Storage : 主要邏輯實踐的地方，用來控制貨物生產與消費**  
儲存貨物的地方，實作produce、consume的邏輯。

**2. Producer : 生產貨物**  
呼叫 Storage produce() 來生產貨物

**3. Consumer : 消費貨物**  
呼叫 Storage consume() 來消費貨物

**4. StorageApplication : 主要程式的入口**    
呼叫main()

## 三. 執行結果
**1. 使用非公平鎖**  
```Storage.java : new ReentrantLock();```  
非公平鎖的thread在嘗試獲取鎖之前會執行2次CAS(compare and swap)運算，  
若發現鎖空閒則直接獲得鎖，如果2次CAS運算都未能獲得鎖則進入等待列隊。  
由於thread掛起或喚醒```await()``` ```signal()```都需要消耗資源，  
非公平鎖能更少的掛起喚醒以提升效能，但也可能造成飢餓問題(某個thread長時間沒有獲得鎖)。

**2. 使用公平鎖**  
```Storage.java : new ReentrantLock(true);```  
公平鎖即競爭鎖的幾個thread，會依照FIFO的方式依序取得鎖的資源，對於競爭者是公平的。
