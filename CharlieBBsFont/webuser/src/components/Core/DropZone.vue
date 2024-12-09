<template>
  <div id="dropzone" ref="dropzoneElement" class="dropzone"></div>
</template>

<script>
import { ref, onMounted } from 'vue';
import Dropzone from 'dropzone';
import 'dropzone/dist/dropzone.css';

export default {
  props: ['initialImage'],
  setup(props, { emit }) {
    const dropzoneElement = ref(null);

    onMounted(() => {
      const myDropzone = new Dropzone(dropzoneElement.value, {
        url: 'https://httpbin.org/post',
        thumbnailWidth: 150,
        maxFilesize: 1,
        headers: { 'My-Awesome-Header': 'header value' },
        uploadMultiple: false,
        maxFiles: 1,
        addRemoveLinks: true,
        acceptedFiles: 'image/*',
        init: function () {
          this.on('addedfile', function (file) {
            if (!file.status) return;
            emit('addImage', file);
          });

          if (props.initialImage) {
            const mime = props.initialImage.substring(props.initialImage.indexOf(':') + 1, props.initialImage.indexOf(';'));
            const extension = mime.split('/')[1];
            const file = { name: `filename.${extension}`, type: mime, dataURL: props.initialImage };

            this.emit('addedfile', file);
            this.emit('thumbnail', file, file.dataURL);
            this.emit('complete', file);
          }
        }
      });
    });

    return {
      dropzoneElement
    };
  }
};
</script>

<style lang="scss">
.dz-preview, .dz-image {
  width: 100%;
  margin: 0 !important;
}
.dropzone img {
  margin: 0 auto;
  max-width: 100%;
  max-height: 300px;
}
.dz-details {
  background-color: rgba(0, 0, 0, 0.3) !important;
}
.dz-size, .dz-filename, .dz-progress {
  display: none;
}
</style>
