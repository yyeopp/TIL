<template>
  <div>
    <h2>{{ msg }}</h2>
    <ul>
      <li>
        <router-link to="/book/create">도서 등록</router-link>
      </li>
      <li>
        <router-link to="/book/list">도서 목록</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    msg: String,
  },
};
</script>
