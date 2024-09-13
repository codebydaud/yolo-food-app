<template>
  <div class="col-md-8 offset-md-4">
    <button class="btn btn-danger marginme" @click="deleteItem">
      Delete
    </button>
    <button class="btn btn-primary" @click="updateItem">
      Update
    </button>
  </div>
</template>

<script>
export default {
  methods: {
    async deleteItem() {
      console.log('delete is called, update should not');
    },
    async updateItem() {
      console.log('update is called, delete should not');
    },
  },
}
</script>